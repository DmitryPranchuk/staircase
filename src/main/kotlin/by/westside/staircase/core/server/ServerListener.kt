package by.westside.staircase.core.server

import by.westside.staircase.core.http.request.HttpRequest
import by.westside.staircase.core.http.request.RequestType

/**
 * Created by d.pranchuk on 1/24/16.
 */
interface  ServerListener {

    fun getPath() : String

    fun getRequestType() : RequestType

    fun processRequest(httpRequest: HttpRequest) : String

}