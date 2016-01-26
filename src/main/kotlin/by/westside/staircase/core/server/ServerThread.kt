package by.westside.staircase.core.server

import by.westside.staircase.core.exception.StaircaseException
import by.westside.staircase.core.http.request.HttpRequest
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
        }
    }

    private fun processRequest(httpRequest: HttpRequest, socket: Socket) {
        val serverListener = syncServer.getListener(httpRequest.path, httpRequest.requestType)
        val response = serverListener.process(httpRequest)
        BufferedWriter(OutputStreamWriter(socket.outputStream)).use { out ->
            writeResponse(HttpUtil.composeHttpResponse(response), out)
        }
    }

    private fun getRequest(socket: Socket): HttpRequest {
        val input = BufferedReader(InputStreamReader(socket.inputStream, "UTF-8"))
        val request = IOUtil.readStream(input)
        return HttpUtil.parseHttpRequest(request)
    }

    private fun writeResponse(response: String, out: BufferedWriter) {
        out.write(response)
    }

}