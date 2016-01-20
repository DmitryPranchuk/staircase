package by.westside.staircase.core.server

import by.westside.staircase.core.util.HttpUtil
import by.westside.staircase.core.util.IOUtil
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.ServerSocket

/**
 * Created by d.pranchuk on 1/20/16.
 */
class SyncServer(val port: Int = 8080) {

    fun start() {
        println("$port port is listening by staircase")
        ServerSocket(port).use { serverSocket ->
            while (true) {
                serverSocket.accept().use { socket ->
                    val input = BufferedReader(InputStreamReader(socket.inputStream, "UTF-8"))
                    val out = BufferedWriter(OutputStreamWriter(socket.outputStream))
                    val request = IOUtil.readStream(input)
                    val httpRequest = HttpUtil.parseHttpRequest(request)
                    writeResponse("Hello world!", out)
                }
            }
        }
    }

    fun writeResponse(response: String, out: BufferedWriter) {
        out.write("\r\n")
        out.write(response)
        out.close()
    }
}