package by.westside.staircase.core.http.request

/**
 * Created by d.pranchuk on 1/23/16.
 */
enum class HttpVersion(val value: String) {
    ZERO_NINE("HTTP/0.9"),
    ONE_ZERO("HTTP/1.0"),
    ONE_ONE("HTTP/1.1"),
    ONE_TWO("HTTP/1.2"),
    TWO("HTTP/2"),
    UNKNOWN("")
}