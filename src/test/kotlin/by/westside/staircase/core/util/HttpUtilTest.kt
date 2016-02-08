package by.westside.staircase.core.util

import by.westside.staircase.core.exception.RequestFormatException
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.shouldThrow
import kotlin.test.assertEquals

/**
 * Created by d.pranchuk on 1/25/16.
 */
class HttpUtilTest : Spek() {init {

    given("Bad request with incorrect first line") {
        val badRequest = "INCORRECT LINE\n Host: mail.example.com\n" +
                "Referer: http://mail.example.com/send-message.html\n" +
                "User-Agent: BrowserForDummies/4.67b\n" +
                "Content-Type: multipart/form-data; boundary=\"Asrf456BGe4h\""
        on("httpRequest parsing") {
            fun parsing() = HttpUtil.parseHttpRequest(badRequest)
            it("should throw exception") {
                val e = shouldThrow(RequestFormatException::class.java, { parsing() })
                assertEquals("Incorrect first line: INCORRECT LINE", e.message)
            }
        }
    }
}
}