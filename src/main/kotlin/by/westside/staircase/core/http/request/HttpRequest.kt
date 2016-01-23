package by.westside.staircase.core.http.request

/**
 * Created by d.pranchuk on 1/20/16.
 */
data class HttpRequest(
        val headers: Map<String, String>,
        val body: String,
        val requestType: RequestType,
        val httpVersion: HttpVersion,
        val path: String) {
}