package uk.co.droidinactu.tpximpacttask.urlshortener.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import uk.co.droidinactu.tpximpacttask.exception.AliasNotFoundException;
import uk.co.droidinactu.tpximpacttask.urlshortener.dto.ShortenUrlRequest;
import uk.co.droidinactu.tpximpacttask.urlshortener.dto.ShortenUrlResponse;
import uk.co.droidinactu.tpximpacttask.urlshortener.dto.UrlMappingDto;
import uk.co.droidinactu.tpximpacttask.urlshortener.service.UrlShortenerService;

import java.util.List;

/**
 * Controller for URL shortener API endpoints.
 * Implements the OpenAPI spec.
 */
@RestController
@Slf4j
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    @Autowired
    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    /**
     * Endpoint to shorten a URL.
     * POST /shorten
     *
     * @param request        The request containing the URL to shorten and optional custom alias
     * @param servletRequest The HTTP request
     * @return A response containing the shortened URL
     */
    @PostMapping("/shorten")
    public ResponseEntity<ShortenUrlResponse> shortenUrl(
            @Valid @RequestBody ShortenUrlRequest request,
            HttpServletRequest servletRequest) {
        ShortenUrlResponse response = urlShortenerService.shortenUrl(request, servletRequest);
        log.info("URL Shortened from {} to {}", request.getFullUrl(), response.getShortUrl());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Endpoint to redirect to the full URL.
     * GET /{alias}
     *
     * @param alias The alias to look up
     * @return A redirect to the full URL
     */
    @GetMapping("/{alias}")
    public RedirectView redirectToFullUrl(@PathVariable String alias) {
        String fullUrl = urlShortenerService.getFullUrl(alias);
        return new RedirectView(fullUrl);
    }

    /**
     * Custom exception handler for the redirect endpoint.
     * This is needed because we want to return a redirect view, not a JSON response.
     *
     * @param ex The exception
     * @return A redirect to the error page with 404 status
     */
    @ExceptionHandler(AliasNotFoundException.class)
    public RedirectView handleAliasNotFoundException(AliasNotFoundException ex) {
        RedirectView redirectView = new RedirectView("/error");
        redirectView.setStatusCode(HttpStatus.NOT_FOUND);
        return redirectView;
    }

    /**
     * Endpoint to delete a shortened URL.
     * DELETE /{alias}
     *
     * @param alias The alias to delete
     * @return No content if successful, not found if alias doesn't exist
     */
    @DeleteMapping("/{alias}")
    public ResponseEntity<Void> deleteUrlMapping(@PathVariable String alias) {
        urlShortenerService.deleteUrlMapping(alias);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Endpoint to list all shortened URLs.
     * GET /urls
     *
     * @return A list of all shortened URLs
     */
    @GetMapping("/urls")
    public ResponseEntity<List<UrlMappingDto>> getAllUrlMappings() {
        List<UrlMappingDto> urlMappings = urlShortenerService.getAllUrlMappings();
        return new ResponseEntity<>(urlMappings, HttpStatus.OK);
    }
}
