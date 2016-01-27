package by.westside.staircase.core.server

import by.westside.staircase.core.exception.StaircaseException
import by.westside.staircase.core.http.request.HttpRequest
import by.westside.staircase.core.http.request.RequestType
import by.westside.staircase.core.http.response.HttpResponse
import by.westside.staircase.core.http.response.ResponseStatus
import java.net.ServerSocket
import java.net.SocketException
import java.util.*
import java.util.concurrent.Executors

/**
 * Created by d.pranchuk on 1/20/16.c
 */
class SyncServer(val port: Int = 80) : Runnable {

    val serverSocket = ServerSocket(port)
    private var isRunning = true
    private val pool = Executors.newCachedThreadPool()
    private val listeners = HashMap<Method, (HttpRequest) -> Any>()

    override fun run() {
        start()
    }

    fun stop() {
        isRunning = false
        pool.shutdown()
        serverSocket.close()
    }

    fun registerListener(path: String, requestType: RequestType, callback: (HttpRequest) -> Any) {
        if (listeners.get(Method(path, requestType)) == null) {
            listeners.put(Method(path, requestType), callback)
        } else {
            throw StaircaseException("Listener for method $requestType $path already registered")
        }
    }

    internal fun getListener(path: String, requestType: RequestType): (HttpRequest) -> Any {
        return listeners.get(Method(path, requestType)) ?: { request ->
            HttpResponse(responseStatus = ResponseStatus.NOT_FOUND, body = "<h1>NOT FOUND</h1>")
        }
    }

    private fun start() {
        println("$port port is listening by staircase")
        while (isRunning) {
            try {
                val socket = serverSocket.accept()
                pool.execute(ServerThread(socket, this))
            } catch(e: SocketException) {
            }
        }
        println("Server was stopped")
    }
}
