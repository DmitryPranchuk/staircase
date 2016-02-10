package by.westside.staircase.app

import by.westside.staircase.core.http.request.RequestType
import by.westside.staircase.core.http.response.HttpResponse
import by.westside.staircase.core.server.startSyncServer
import java.util.*

/**
 * Created by d.pranchuk on 1/20/16.
 */
fun main(args: Array<String>) {
    val syncServer = startSyncServer(9000)
    syncServer.get("/hello", { request ->
        Thread.sleep(1000)
        HttpResponse("Hello! Now is ${Date().time}")
    })
    syncServer.registerListener("/patch", RequestType.PATCH, { request ->
        request.body
    })
    syncServer.get("/loaderio-92dba1a89b55cf86eb150e1ecf14aee0/", { req ->
        HttpResponse("loaderio-92dba1a89b55cf86eb150e1ecf14aee0")
    })
}
