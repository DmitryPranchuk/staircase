package by.westside.staircase.app

import by.westside.staircase.core.http.request.HttpRequest
import by.westside.staircase.core.http.request.RequestType
import by.westside.staircase.core.server.ServerListener
import java.util.*

/**
 * Created by d.pranchuk on 1/24/16.
 */
class HelloWorldListener : ServerListener {

    override fun getPath(): String {
        return "/hello"
    }

    override fun getRequestType(): RequestType {
        return RequestType.GET
    }

    override fun processRequest(httpRequest: HttpRequest): String {
        return "Hello! Now is ${Date().time}"
    }
}