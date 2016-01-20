package by.westside.staircase.core.server

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
                    var line: String? = input.readLine()
                    while (line != null && line != "") {
                        println(line)
                        line = input.readLine()
                    }
                    println("\n")
                    println(readFromBuffer(input))
                    println("\n")
                    out.write("HTTP/1.0 200 OK\r\n")
                    out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n")
                    out.write("Server: Apache/0.8.4\r\n")
                    out.write("Content-Type: text/html\r\n")
                    out.write("Content-Length: 23\r\n")
                    out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n")
                    out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n")
                    out.write("\r\n")
                    out.write("<TITLE>response</TITLE>")
                    out.close()
                }
            }
        }
    }

    fun readFromBuffer(input: BufferedReader): String {
        val stringBuilder = StringBuilder()
        while (input.ready()) {
            stringBuilder.append(input.read().toChar())
        }
        return stringBuilder.toString()
    }
}