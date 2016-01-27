package by.westside.staircase.core.http.response

import by.westside.staircase.core.http.request.HttpVersion
import java.util.*

/**
 * Created by d.pranchuk on 1/25/16.
 */
data class HttpResponse(val body: String = String(),
                        val headers: Map<String, String> = HashMap<String, String>(),
                        val responseStatus: ResponseStatus = ResponseStatus.OK,
                        val httpVersion: HttpVersion = HttpVersion.UNKNOWN) {

    fun setHttpVersionIfUnknown(httpVersion: HttpVersion): HttpResponse {
        return if (this.httpVersion == HttpVersion.UNKNOWN) this.copy(httpVersion = httpVersion) else this
    }

}
