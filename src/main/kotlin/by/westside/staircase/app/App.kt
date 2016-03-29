package by.westside.staircase.app

import by.westside.staircase.core.http.response.HttpResponse
import by.westside.staircase.core.server.startSyncServer
import java.util.Date

/**
 * Created by d.pranchuk on 1/20/16.
 */
fun main(args: Array<String>) {
    val syncServer = startSyncServer(9000)
    syncServer.get("/hello", { request ->
        Thread.sleep(1000)
        HttpResponse("Hello! Now is ${Date().time}")
    })
}
