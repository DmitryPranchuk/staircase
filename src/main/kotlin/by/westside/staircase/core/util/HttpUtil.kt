package by.westside.staircase.core.util

import by.westside.staircase.core.http.request.HttpRequest
import java.util.*

/**
 * Created by d.pranchuk on 1/20/16.
 */
object HttpUtil {
    fun parseHttpRequest(stringRequest: String) : HttpRequest {
        var headers = HashMap<String, String>()
        val separatedLines = stringRequest.split(System.getProperty("line.separator"))
        var body = ""
        (1..separatedLines.size-1).forEach { index ->
            if(separatedLines.get(index).isBlank()) {
                (index+1..separatedLines.size-1).forEach { bodyIndex ->
                    body += separatedLines.get(bodyIndex)
                }
                return@forEach
            }
            val header = parseHeader(separatedLines.get(index))
            headers.put(header.first, header.second)
        }
        return HttpRequest(headers, body, separatedLines.first())
    }
}

fun parseHeader(header: String) : Pair<String, String> {
    val splittedHeader = header.split(delimiters = ":", limit = 2)
    return Pair(splittedHeader.first(), splittedHeader.last())
}