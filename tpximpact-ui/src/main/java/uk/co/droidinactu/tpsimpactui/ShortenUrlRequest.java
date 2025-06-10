package uk.co.droidinactu.tpsimpactui;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for the request to shorten a URL.
 * Based on the OpenAPI spec for the POST /shorten endpoint.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShortenUrlRequest {

    /**
     * The full URL to be shortened.
     * This field is required.
     */
    private String fullUrl;

    /**
     * Optional custom alias for the shortened URL.
     * If not provided, a random alias will be generated.
     */
    private String customAlias;
}