package com.elefants.shorturl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static junit.framework.TestCase.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.elefants.shorturl.url.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UrlServiceTests {
    /*@Mock
    private UrlRepository repository;

    @InjectMocks
    private UrlService urlService;

    @BeforeEach
    public void setUp() {
        //urlService = new UrlService();
    }

    @Test
    public void testGenerateShortUrl_length() {
        String shortUrl = urlService.generateShortUrl();
        assertTrue(shortUrl.length() >= 6 && shortUrl.length() <= 8, "Short URL length is outside expected range");
    }

    @Test
    public void testGenerateShortUrl_characterSet() {
        String shortUrl = urlService.generateShortUrl();
        String allowedChars = "abcdefghijklmnopqrstuvwxyz0123456789";
        for (char c : shortUrl.toCharArray()) {
            assertTrue(allowedChars.indexOf(c) != -1, "Short URL contains character not allowed in set");
        }
    }

    @Test
    public void testValidateCreateFields_validUrl_success() throws IOException {
        HttpURLConnection connection = Mockito.mock(HttpURLConnection.class);
        Mockito.when(connection.getResponseCode()).thenReturn(200);
        URL url = Mockito.mock(URL.class);
        Mockito.when(url.openConnection()).thenReturn(connection);
        UrlCreateRequest request = new UrlCreateRequest("https://www.example.com");
        Optional<UrlCreateResponse.Error> validationError = urlService.validateCreateFields(request);
        assertEquals(Optional.empty(), validationError);
    }

    @Test
    public void testValidateCreateFields_invalidUrl_responseCode() throws IOException {
        HttpURLConnection connection = Mockito.mock(HttpURLConnection.class);
        Mockito.when(connection.getResponseCode()).thenReturn(404);
        URL url = Mockito.mock(URL.class);
        Mockito.when(url.openConnection()).thenReturn(connection);
        UrlCreateRequest request = new UrlCreateRequest("invalid_url");
        Optional<UrlCreateResponse.Error> validationError = urlService.validateCreateFields(request);
        assertEquals(Optional.of(UrlCreateResponse.Error.INVALID_LONGURL), validationError);
    }

    @Test
    public void testValidateCreateFields_invalidUrl_exception() throws IOException {
        URL url = Mockito.mock(URL.class);
        Mockito.when(url.openConnection()).thenThrow(new MalformedURLException("Invalid URL"));
        UrlCreateRequest request = new UrlCreateRequest("invalid_url");
        Optional<UrlCreateResponse.Error> validationError = urlService.validateCreateFields(request);
        assertEquals(Optional.of(UrlCreateResponse.Error.INVALID_LONGURL), validationError);
    }

    @Test
    public void testValidateCreateFields_emptyUrl() {
        UrlCreateRequest request = new UrlCreateRequest("");
        Optional<UrlCreateResponse.Error> validationError = urlService.validateCreateFields(request);
        assertEquals(Optional.of(UrlCreateResponse.Error.INVALID_LONGURL), validationError);
    }*/
}
