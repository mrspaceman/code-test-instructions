package uk.co.droidinactu.tpximpacttask.urlshortener.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity class representing a URL mapping.
 */
@Entity
@Table(name = "url_mappings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlMapping {

    /**
     * The alias/short identifier for the URL.
     * This is the primary key.
     */
    @Id
    @Column(nullable = false, unique = true)
    private String alias;

    /**
     * The original/full URL.
     */
    @Column(nullable = false)
    private String fullUrl;

    /**
     * The complete shortened URL.
     */
    @Column(nullable = false)
    private String shortUrl;

    /**
     * true indicates that this shortened url was created using a user supplied custom string.
     */
    @Column(nullable = false)
    private Boolean isCusomised = false;

    /**
     * The date and time when the URL mapping was created.
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;
}