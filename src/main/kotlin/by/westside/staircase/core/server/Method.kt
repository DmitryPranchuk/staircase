package by.westside.staircase.core.server

import by.westside.staircase.core.http.request.RequestType

/**
 * Created by d.pranchuk on 1/27/16.
 */
data class Method(val path: String, val requestType: RequestType)