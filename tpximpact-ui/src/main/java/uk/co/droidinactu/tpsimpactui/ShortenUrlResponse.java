package uk.co.droidinactu.tpsimpactui;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for the response from shortening a URL.
 * Based on the OpenAPI spec for the POST /shorten endpoint response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortenUrlResponse {

    /**
     * The shortened URL (base URL + alias).
     */
    private String shortUrl;
}