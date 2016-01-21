package by.westside.staircase.core.server

import by.westside.staircase.core.util.HttpUtil
import by.westside.staircase.core.util.IOUtil
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.ServerSocket
import java.util.*

/**
 * Created by d.pranchuk on 1/20/16.
 */
class SyncServer(val port: Int = 8080) {

    fun start() {
        println("$port port is listening by staircase")
        ServerSocket(port).use { serverSocket ->
            while (true) {
                serverSocket.accept().use { socket ->
                    var start = Date().time
                    val input = BufferedReader(InputStreamReader(socket.inputStream, "UTF-8"))
                    val request = IOUtil.readStream(input)
                    val httpRequest = HttpUtil.parseHttpRequest(request)
                    println(httpRequest)
                    BufferedWriter(OutputStreamWriter(socket.outputStream)).use { out ->
                        writeResponse("Hello world!", out)
                    }
                    println("processed for ${Date().time - start} ms")
                }
            }
        }
    }

    fun writeResponse(response: String, out: BufferedWriter) {
        out.write("\r\n")
        out.write(response)
    }
}