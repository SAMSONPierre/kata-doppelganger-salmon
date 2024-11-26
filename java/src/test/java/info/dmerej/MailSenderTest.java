package info.dmerej;

import info.dmerej.mailprovider.SendMailRequest;
import info.dmerej.mailprovider.SendMailResponse;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MailSenderTest {

    User user1 = new User("toto", "toto@gmail.com");

    testHttpClient testHttpClient = new testHttpClient();

    MailSender mailSender = new MailSender(testHttpClient);

    static class testHttpClient implements HttpClient {
        private final HashMap<User, Integer> notifications = new HashMap<>();

        private SendMailRequest sendMailRequest;


        @Override
        public SendMailResponse post(String url, Object request) {
            sendMailRequest = ((SendMailRequest) request);

            return null;
        }

        public SendMailRequest getSendMailRequest() {
            return sendMailRequest;
        }
    }

    @Test
    void should_make_a_valid_http_request() {
        // TODO: write a test to demonstrate the bug in MailSender.sendV1()

        mailSender.sendV1(user1, "message");

        assertEquals("toto@gmail.com", testHttpClient.getSendMailRequest().recipient());
        assertEquals("New notification", testHttpClient.getSendMailRequest().subject());
        assertEquals("message", testHttpClient.getSendMailRequest().body());
    }

    @Test
    void should_retry_when_getting_a_503_error() {
        // TODO: write a test to demonstrate the bug in MailSender.sendV2()
    }
}
