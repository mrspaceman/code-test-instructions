package uk.co.droidinactu.tpximpacttask.urlshortener.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.view.RedirectView;
import uk.co.droidinactu.tpximpacttask.exception.AliasNotFoundException;
import uk.co.droidinactu.tpximpacttask.urlshortener.dto.ShortenUrlRequest;
import uk.co.droidinactu.tpximpacttask.urlshortener.dto.ShortenUrlResponse;
import uk.co.droidinactu.tpximpacttask.urlshortener.service.UrlShortenerService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UrlShortenerControllerTest {

    @Mock
    private UrlShortenerService urlShortenerService;

    @InjectMocks
    private UrlShortenerController urlShortenerController;

    private MockHttpServletRequest mockRequest;
    private ShortenUrlRequest shortenUrlRequest;

    @BeforeEach
    void setUp() {
        mockRequest = new MockHttpServletRequest();
        shortenUrlRequest = new ShortenUrlRequest();
        shortenUrlRequest.setFullUrl("https://example.com");
        shortenUrlRequest.setCustomAlias("custom");
    }

    @Test
    void shortenUrl_ShouldReturnCreatedStatus() {
        // Arrange
        ShortenUrlResponse expectedResponse = new ShortenUrlResponse();
        expectedResponse.setShortUrl("http://localhost/custom");
        when(urlShortenerService.shortenUrl(any(), any())).thenReturn(expectedResponse);

        // Act
        ResponseEntity<ShortenUrlResponse> response =
                urlShortenerController.shortenUrl(shortenUrlRequest, mockRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedResponse.getShortUrl(), response.getBody().getShortUrl());
        verify(urlShortenerService).shortenUrl(any(), any());
    }

    @Test
    void redirectToFullUrl_ShouldReturnRedirectView() {
        // Arrange
        String alias = "custom";
        String expectedUrl = "https://example.com";
        when(urlShortenerService.getFullUrl(alias)).thenReturn(expectedUrl);

        // Act
        RedirectView redirectView = urlShortenerController.redirectToFullUrl(alias);

        // Assert
        assertEquals(expectedUrl, redirectView.getUrl());
        verify(urlShortenerService).getFullUrl(alias);
    }

    @Test
    void handleAliasNotFoundException_ShouldReturnNotFoundStatus() {
        // Arrange
        AliasNotFoundException exception = new AliasNotFoundException("Alias not found");

        // Act
        RedirectView redirectView = urlShortenerController.handleAliasNotFoundException(exception);

        // Assert
        assertEquals("/error", redirectView.getUrl());
    }

    @Test
    void deleteUrlMapping_ShouldReturnNoContent() {
        // Arrange
        String alias = "custom";

        // Act
        ResponseEntity<Void> response = urlShortenerController.deleteUrlMapping(alias);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(urlShortenerService).deleteUrlMapping(alias);
    }

    @Test
    void deleteUrlMapping_WhenAliasNotFound_ShouldThrowException() {
        // Arrange
        String alias = "nonexistent";
        doThrow(new AliasNotFoundException("Alias not found"))
                .when(urlShortenerService).deleteUrlMapping(alias);

        // Act & Assert
        assertThrows(AliasNotFoundException.class, () ->
                urlShortenerController.deleteUrlMapping(alias));
        verify(urlShortenerService).deleteUrlMapping(alias);
    }
}
