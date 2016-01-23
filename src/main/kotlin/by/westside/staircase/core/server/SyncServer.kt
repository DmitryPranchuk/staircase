package by.westside.staircase.core.server

import java.net.ServerSocket

/**
 * Created by d.pranchuk on 1/20/16.c
 */
class SyncServer(val port: Int = 80, val threadsCount: Int) : Runnable {

    var thread: Thread? = null
    val serverSocket = ServerSocket(port)

    override fun run() {
        synchronized(this) {
            thread = Thread.currentThread()
        }
        start()
    }

    private fun start() {
        println("$port port is listening by staircase")
        (1..threadsCount).forEach {
            Thread(ServerThread(serverSocket, it)).start()
        }
    }
}
