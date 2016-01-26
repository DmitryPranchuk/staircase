package by.westside.staircase.app

import by.westside.staircase.core.http.request.RequestType
import by.westside.staircase.core.http.response.HttpResponse
import by.westside.staircase.core.http.response.ResponseStatus
import by.westside.staircase.core.server.ServerListener
import by.westside.staircase.core.server.SyncServer
import by.westside.staircase.core.server.startSyncServer
import java.util.*

/**
 * Created by d.pranchuk on 1/20/16.
 */
fun main(args: Array<String>) {
    val syncServer = startSyncServer(9000)
    syncServer.registerListener(ServerListener("/hello", RequestType.GET, { request ->
        Thread.sleep(1000)
        HttpResponse(httpVersion = request.httpVersion,
                responseStatus = ResponseStatus.OK,
                body = "Hello! Now is ${Date().time}")
    }))
    syncServer.registerListener(ServerListener("/loaderio-92dba1a89b55cf86eb150e1ecf14aee0/", RequestType.GET, { req ->
        HttpResponse(req.httpVersion, ResponseStatus.OK, "loaderio-92dba1a89b55cf86eb150e1ecf14aee0")
    }))
}
