package by.westside.staircase.core.util

import java.io.BufferedReader

/**
 * Created by d.pranchuk on 1/20/16.
 */
object IOUtil {
    fun readStream(input: BufferedReader): String {
        var result = ""
        var line: String? = input.readLine()
        while (line != null && line != "") {
            result += line
            result += "\n"
            line = input.readLine()
        }
        result += "\n"
        result += readFromBuffer(input)
        return result
    }
}

fun readFromBuffer(input: BufferedReader): String {
    val stringBuilder = StringBuilder()
    while (input.ready()) {
        stringBuilder.append(input.read().toChar())
    }
    return stringBuilder.toString()
}
