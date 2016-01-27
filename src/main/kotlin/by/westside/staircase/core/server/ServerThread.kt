package by.westside.staircase.core.server

import by.westside.staircase.core.exception.StaircaseException
import by.westside.staircase.core.http.request.HttpRequest
import by.westside.staircase.core.http.response.HttpResponse
import by.westside.staircase.core.http.response.ResponseStatus
import by.westside.staircase.core.util.HttpUtil
import by.westside.staircase.core.util.IOUtil
import by.westside.staircase.core.util.executionTime
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket

/**
 * Created by d.pranchuk on 1/22/16.
 */
internal class ServerThread(val socket: Socket, val syncServer: SyncServer) : Runnable {

    private var isResponded = false

    override fun run() {
        val executionTime = executionTime {
            acceptConnection(socket)
        }
        println("executed for $executionTime ms")
    }

    private fun acceptConnection(socket: Socket) {
        try {
            val httpRequest = getRequest(socket)
            println(httpRequest)
            processRequest(httpRequest, socket)
        } catch(e: StaircaseException) {
            println(e)
            if (!isResponded) {
                writeHttpResponse(socket, HttpResponse(body = "Server Error", responseStatus = ResponseStatus.INTERNAL_SERVER_ERROR))
            }
        }
    }

    private fun processRequest(httpRequest: HttpRequest, socket: Socket) {
        val serverListener = syncServer.getListener(httpRequest.path, httpRequest.requestType)
        val response = serverListener(httpRequest)
        val processedResponse = processResponse(response, httpRequest)
        writeHttpResponse(socket, processedResponse)
    }

    private fun processResponse(response: Any, httpRequest: HttpRequest): HttpResponse {
        return when (response) {
            is String -> HttpResponse(body = response, responseStatus = ResponseStatus.OK, httpVersion = httpRequest.httpVersion)
            is HttpResponse -> {
                response.setHttpVersionIfUnknown(httpRequest.httpVersion)
            }
            else -> HttpResponse(body = response.toString(), responseStatus = ResponseStatus.OK, httpVersion = httpRequest.httpVersion)
        }
    }

    private fun writeHttpResponse(socket: Socket, httpResponse: HttpResponse) {
        BufferedWriter(OutputStreamWriter(socket.outputStream)).use { out ->
            writeResponse(HttpUtil.composeHttpResponse(httpResponse), out)
        }
    }

    private fun getRequest(socket: Socket): HttpRequest {
        val input = BufferedReader(InputStreamReader(socket.inputStream, "UTF-8"))
        val request = IOUtil.readStream(input)
        return HttpUtil.parseHttpRequest(request)
    }

    private fun writeResponse(response: String, out: BufferedWriter) {
        out.write(response)
        isResponded = true
    }

}