package by.westside.staircase.app

import by.westside.staircase.core.http.request.RequestType
import by.westside.staircase.core.http.response.HttpResponse
import by.westside.staircase.core.http.response.ResponseStatus
import by.westside.staircase.core.server.ServerListener
import by.westside.staircase.core.server.SyncServer
import java.util.*

/**
 * Created by d.pranchuk on 1/20/16.
 */
fun main(args: Array<String>) {
    val syncServer = SyncServer(9000, 4)
    syncServer.registerListener(ServerListener("/hello", RequestType.GET, { request ->
        HttpResponse(httpVersion = request.httpVersion,
                responseStatus = ResponseStatus.OK,
                body = "Hello! Now is ${Date().time}")
    }))
    Thread(syncServer).start()
}
