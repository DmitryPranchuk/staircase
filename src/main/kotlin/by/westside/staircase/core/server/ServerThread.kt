package by.westside.staircase.core.server

import by.westside.staircase.core.util.HttpUtil
import by.westside.staircase.core.util.IOUtil
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket
import java.util.*

/**
 * Created by d.pranchuk on 1/22/16.
 */
class ServerThread(val threadId: Int, val syncServer: SyncServer) : Runnable {

    override fun run() {
        println("thread $threadId started")
        syncServer.serverSocket.use { serverSocket ->
            while (true) {
                serverSocket.accept().use { socket ->
                    val start = Date().time
                    val input = BufferedReader(InputStreamReader(socket.inputStream, "UTF-8"))
                    val request = IOUtil.readStream(input)
                    val httpRequest = HttpUtil.parseHttpRequest(request)
                    println(httpRequest)
                    val serverListener = syncServer.getListener(httpRequest.path, httpRequest.requestType)
                    val response = serverListener?.processRequest(httpRequest)
                    BufferedWriter(OutputStreamWriter(socket.outputStream)).use { out ->
                        writeResponse(response ?: "not found", out)
                    }
                    println("processed for ${Date().time - start} ms by thread $threadId")
                }
            }
        }
    }

    fun writeResponse(response: String, out: BufferedWriter) {
        out.write(response)
    }

    fun writeLoaderIo(socket: Socket) {
        val token = "loaderio-92dba1a89b55cf86eb150e1ecf14aee0"
        socket.outputStream.write(token.toByteArray(), 0, token.length)
        socket.outputStream.flush()
    }
}