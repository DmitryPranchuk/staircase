package by.westside.staircase.core.util

import by.westside.staircase.core.exception.RequestFormatException
import by.westside.staircase.core.http.request.HttpRequest
import by.westside.staircase.core.http.request.HttpVersion
import by.westside.staircase.core.http.request.RequestType
import by.westside.staircase.core.http.response.HttpResponse
import java.util.*

/**
 * Created by d.pranchuk on 1/20/16.
 */
object HttpUtil {

    fun parseHttpRequest(stringRequest: String): HttpRequest {
        var headers = HashMap<String, String>()
        val separatedLines = stringRequest.split(System.getProperty("line.separator"))
        val firstLineParams = separatedLines[0].split(delimiters = " ", limit = 3)
        if (firstLineParams.count() != 3 || firstLineParams.any { it.isBlank() }) {
            throw RequestFormatException("Incorrect first line: ${separatedLines[0]}")
        }
        val requestType = parseRequestType(firstLineParams[0])
        val httpVersion = parseHttpVersion(firstLineParams[2])
        var body = ""
        run breaker@ {
            (1..separatedLines.size - 1).forEach { index ->
                if (separatedLines[index].isBlank()) {
                    body = parseBody(separatedLines.subList(index + 1, separatedLines.size))
                    return@breaker
                }
                val header = parseHeader(separatedLines[index])
                headers.put(header.first, header.second)
            }
        }
        return HttpRequest(headers, body, requestType, httpVersion, firstLineParams[1])
    }

    fun composeHttpResponse(httpResponse: HttpResponse) : String {
        val firstLine = "${httpResponse.httpVersion.value} ${httpResponse.responseStatus.code} ${httpResponse.responseStatus.value}\r\n"
        var response = firstLine
        httpResponse.headers.forEach {
            response += "${it.key}: ${it.value}\r\n"
        }
        if(!httpResponse.body.isBlank()) {
            response += "Content-Length: ${httpResponse.body.length}\r\n"
            response += "\r\n"
            response += httpResponse.body
        }
        return response
    }
}

fun parseRequestType(requestType: String): RequestType {
    if (!RequestType.values().map { it.name }.contains(requestType)) {
        throw RequestFormatException("Unknown request type $requestType")
    }
    return RequestType.valueOf(requestType)
}

fun parseHttpVersion(httpVersionString: String): HttpVersion {
    var httpVersion = HttpVersion.UNKNOWN
    HttpVersion.values() .forEach {
        if (httpVersionString.equals(it.value)) httpVersion = it
    }
    if (httpVersion == HttpVersion.UNKNOWN) {
        throw RequestFormatException("Unknown HTTP version $httpVersionString")
    }
    return httpVersion
}

fun parseHeader(header: String): Pair<String, String> {
    val splittedHeader = header.split(delimiters = ":", limit = 2)
    return Pair(splittedHeader.first(), splittedHeader.last())
}

fun parseBody(bodyStrings: List<String>): String {
    if (bodyStrings.isEmpty()) {
        return "";
    } else {
        return bodyStrings.reduce { body, s -> body + "\n" + s }
    }
}