package info.dmerej;

import info.dmerej.mailprovider.SendMailRequest;
import info.dmerej.mailprovider.SendMailResponse;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.*;

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
        // without framework version
        /*
        mailSender.sendV1(user1, "message");

        assertEquals("toto@gmail.com", ((SendMailRequest) testHttpClient.getSendMailRequest()).recipient());
        assertEquals("New notification", ((SendMailRequest) testHttpClient.getSendMailRequest()).subject());
        assertEquals("message", ((SendMailRequest) testHttpClient.getSendMailRequest()).body());
         */

        //with framework version
        HttpClient mockHttpClient = mock(HttpClient.class);
        MailSender mailSender = new MailSender(mockHttpClient);
        ArgumentCaptor<Object> requestCaptor = ArgumentCaptor.forClass(Object.class);

        when(mockHttpClient.post(anyString(), any())).thenReturn(new SendMailResponse(200, "OK"));

        mailSender.sendV1(user1, "message");

        verify(mockHttpClient).post(eq("https://api.mailprovider.com/v3/"), requestCaptor.capture());

        SendMailRequest capturedRequest = (SendMailRequest) requestCaptor.getValue();
        assertEquals("toto@gmail.com", capturedRequest.recipient());
        assertEquals("New notification", capturedRequest.subject());
        assertEquals("message", capturedRequest.body());
    }

    @Test
    void should_retry_when_getting_a_503_error() {
        // version without testing framework
        /*
        mailSender.sendV2(user1, "message");

        assertInstanceOf(SendMailRequest.class, testHttpClient.getSendMailRequest());
         */

        // version with mockito
        HttpClient mockHttpClient = mock(HttpClient.class);
        MailSender mailSender = new MailSender(mockHttpClient);
        SendMailResponse errorResponse = new SendMailResponse(503, "restart");
        when(mockHttpClient.post(anyString(), any())).thenReturn(errorResponse);

        mailSender.sendV2(user1, "message");

        ArgumentCaptor<Object> requestCaptor = ArgumentCaptor.forClass(Object.class);
        verify(mockHttpClient, times(2)).post(eq("https://api.mailprovider.com/v3/"), requestCaptor.capture());
        Object firstRequest = requestCaptor.getAllValues().get(0);
        Object secondRequest = requestCaptor.getAllValues().get(1);

        assertEquals(firstRequest, secondRequest, "The same request should be used for retries.");
    }
}
