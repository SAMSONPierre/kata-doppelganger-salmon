package info.dmerej;

import info.dmerej.mailprovider.SendMailRequest;
import info.dmerej.mailprovider.SendMailResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class MailSenderTest {

    User user1 = new User("toto", "toto@gmail.com");

    testHttpClient testHttpClient = new testHttpClient();

    MailSender mailSender = new MailSender(testHttpClient);

    static class testHttpClient implements HttpClient {
        private Object sendMailRequest;

        @Override
        public SendMailResponse post(String url, Object request) {
            sendMailRequest = request;

            return new SendMailResponse(503, "restart");
        }

        public Object getSendMailRequest() {
            return sendMailRequest;
        }
    }

    @Test
    void should_make_a_valid_http_request() {
        mailSender.sendV1(user1, "message");

        assertEquals("toto@gmail.com", ((SendMailRequest) testHttpClient.getSendMailRequest()).recipient());
        assertEquals("New notification", ((SendMailRequest) testHttpClient.getSendMailRequest()).subject());
        assertEquals("message", ((SendMailRequest) testHttpClient.getSendMailRequest()).body());
    }

    @Test
    void should_retry_when_getting_a_503_error() {
        mailSender.sendV2(user1, "message");

        assertInstanceOf(SendMailRequest.class, testHttpClient.getSendMailRequest());
    }
}
