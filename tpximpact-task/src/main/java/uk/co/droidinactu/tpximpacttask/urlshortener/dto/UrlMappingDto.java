package uk.co.droidinactu.tpximpacttask.urlshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for URL mapping information.
 * Based on the OpenAPI spec for the GET /urls endpoint response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlMappingDto {

    /**
     * The alias/short identifier for the URL.
     */
    private String alias;

    /**
     * The original/full URL.
     */
    private String fullUrl;

    /**
     * The complete shortened URL (base URL + alias).
     */
    private String shortUrl;
}