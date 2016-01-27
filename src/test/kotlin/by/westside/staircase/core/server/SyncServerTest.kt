package by.westside.staircase.core.server

import by.westside.staircase.core.http.request.RequestType
import by.westside.staircase.core.http.response.HttpResponse
import org.jetbrains.spek.api.Spek
import java.net.URL
import kotlin.test.assertEquals

/**
 * Created by d.pranchuk on 1/25/16.
 */
class SyncServerTest : Spek() { init {

    val port = 9000

    given("Server listens $port port with listener on /hello GET") {
        val server = startSyncServer(port)
        val returnedString = "Hello World!"
        server.registerListener(ServerListener("/hello", RequestType.GET, { request ->
            HttpResponse(returnedString)
        }))
        on("GET request on /hello") {
            val response = URL("http://localhost:$port/hello").readText()
            it("should return correct response") {
                assertEquals(returnedString, response)
            }
        }
    }
}
}