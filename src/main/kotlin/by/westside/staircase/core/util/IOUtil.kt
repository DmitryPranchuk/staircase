package by.westside.staircase.core.util

import java.io.BufferedReader

/**
 * Created by d.pranchuk on 1/20/16.
 */
internal object IOUtil {
    fun readStream(input: BufferedReader): String {
        var result = ""
        var contentLength = 0
        run breaker@ {
            input.lineSequence().forEach {
                if (it.isBlank()) return@breaker
                if (it.toLowerCase().contains("content-length")) {
                    contentLength = it.split(':').last().filter { char -> char.isDigit() }.toInt()
                }
                result += it + "\n"
            }
        }
        result += "\n"
        result += readFromBuffer(input, contentLength)
        return result
    }
}

fun readFromBuffer(input: BufferedReader, contentLength: Int): String {
    var body = CharArray(contentLength)
    input.read(body)
    val filteredBody = body.filter { it.toInt() != 0 }
    return filteredBody.joinToString("")
}

