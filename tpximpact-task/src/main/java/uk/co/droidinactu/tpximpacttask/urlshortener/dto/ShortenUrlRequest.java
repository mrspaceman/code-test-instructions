package uk.co.droidinactu.tpximpacttask.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for the request to shorten a URL.
 * Based on the OpenAPI spec for the POST /shorten endpoint.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortenUrlRequest {

    /**
     * The full URL to be shortened.
     * This field is required.
     */
    @NotBlank(message = "Full URL is required")
    private String fullUrl;

    /**
     * Optional custom alias for the shortened URL.
     * If not provided, a random alias will be generated.
     */
    private String customAlias;
}