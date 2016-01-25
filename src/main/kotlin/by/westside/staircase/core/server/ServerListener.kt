package by.westside.staircase.core.server

import by.westside.staircase.core.http.request.HttpRequest
import by.westside.staircase.core.http.request.RequestType
import by.westside.staircase.core.http.response.HttpResponse

/**
 * Created by d.pranchuk on 1/24/16.
 */
data class ServerListener(val path: String, val requestType: RequestType, val process: (HttpRequest) -> HttpResponse)
