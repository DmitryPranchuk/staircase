package by.westside.staircase.core.server

import by.westside.staircase.core.exception.RequestFormatException
import spock.lang.Specification

/**
 * Created by d.pranchuk on 1/23/16.
 */
class SyncServerSpec extends Specification {

    def "should throw exception in incorrect first line case"() {
        given:
        def server = new SyncServer(9000, 1)
        server.run()
        def clientSocket = new Socket("127.0.0.1", 9000)
        def incorrectRequest = "INCORRECT LINE \n Host: mail.example.com\n" +
                "Referer: http://mail.example.com/send-message.html\n" +
                "User-Agent: BrowserForDummies/4.67b\n" +
                "Content-Type: multipart/form-data; boundary=\"Asrf456BGe4h\""
        when:
        def writer = new PrintWriter(clientSocket.outputStream)
        writer.print(incorrectRequest)
        writer.close()
        sleep(1500)
        then:
        def e = thrown(RequestFormatException)
        e.message == "Incorrect first line: INCORRECT LINE"
    }

}
