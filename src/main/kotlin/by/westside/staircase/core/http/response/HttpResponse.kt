package by.westside.staircase.core.http.response

import by.westside.staircase.core.http.request.HttpVersion
import com.sun.deploy.util.StringUtils
import java.util.*

/**
 * Created by d.pranchuk on 1/25/16.
 */
data class HttpResponse(val httpVersion: HttpVersion,
                        val responseStatus: ResponseStatus,
                        val responseCode: Int,
                        val body: String = String(),
                        val headers: Map<String, String> = HashMap<String, String>())
