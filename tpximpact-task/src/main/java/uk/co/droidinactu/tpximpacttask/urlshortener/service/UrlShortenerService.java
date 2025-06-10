package uk.co.droidinactu.tpximpacttask.urlshortener.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.droidinactu.tpximpacttask.exception.AliasNotFoundException;
import uk.co.droidinactu.tpximpacttask.exception.AliasTakenException;
import uk.co.droidinactu.tpximpacttask.urlshortener.dto.ShortenUrlRequest;
import uk.co.droidinactu.tpximpacttask.urlshortener.dto.ShortenUrlResponse;
import uk.co.droidinactu.tpximpacttask.urlshortener.dto.UrlMappingDto;
import uk.co.droidinactu.tpximpacttask.urlshortener.model.UrlMapping;
import uk.co.droidinactu.tpximpacttask.urlshortener.repository.UrlMappingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Service class for URL shortening operations.
 */
@Service
public class UrlShortenerService {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int ALIAS_LENGTH = 6;
    private static final Random RANDOM = new Random();

    private final UrlMappingRepository urlMappingRepository;

    @Autowired
    public UrlShortenerService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    /**
     * Shortens a URL based on the provided request.
     *
     * @param request        The request containing the URL to shorten and optional custom alias
     * @param servletRequest The HTTP request to get the base URL
     * @return A response containing the shortened URL
     * @throws AliasTakenException if the custom alias is already taken
     */
    public ShortenUrlResponse shortenUrl(ShortenUrlRequest request, HttpServletRequest servletRequest) {
        String alias = request.getCustomAlias();
        if (alias == null || alias.trim().isEmpty()) {
            // Generate a random alias if none provided
            alias = generateRandomAlias();
        } else if (urlMappingRepository.existsById(alias)) {
            throw new AliasTakenException("Custom alias is already taken: " + alias);
        }

        String baseUrl = getBaseUrl(servletRequest);
        String shortUrl = baseUrl + "/" + alias;

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setAlias(alias);
        urlMapping.setFullUrl(request.getFullUrl());
        urlMapping.setShortUrl(shortUrl);
        urlMapping.setCreatedAt(LocalDateTime.now());

        urlMappingRepository.save(urlMapping);

        return new ShortenUrlResponse(shortUrl);
    }

    /**
     * Retrieves the full URL for the given alias.
     *
     * @param alias The alias to look up
     * @return The full URL if found
     * @throws AliasNotFoundException if the alias is not found
     */
    public String getFullUrl(String alias) {
        return urlMappingRepository.findById(alias)
                .orElseThrow(() -> new AliasNotFoundException("Alias not found: " + alias))
                .getFullUrl();
    }

    /**
     * Deletes a URL mapping by alias.
     *
     * @param alias The alias to delete
     * @throws AliasNotFoundException if the alias is not found
     */
    public void deleteUrlMapping(String alias) {
        if (!urlMappingRepository.existsById(alias)) {
            throw new AliasNotFoundException("Alias not found: " + alias);
        }
        urlMappingRepository.deleteById(alias);
    }

    /**
     * Lists all URL mappings.
     *
     * @return A list of all URL mappings
     */
    public List<UrlMappingDto> getAllUrlMappings() {
        return urlMappingRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Converts a UrlMapping entity to a UrlMappingDto.
     *
     * @param urlMapping The entity to convert
     * @return The converted DTO
     */
    private UrlMappingDto convertToDto(UrlMapping urlMapping) {
        return new UrlMappingDto(
                urlMapping.getAlias(),
                urlMapping.getFullUrl(),
                urlMapping.getShortUrl()
        );
    }

    /**
     * Generates a random alias of the specified length.
     *
     * @return A random alias
     */
    private String generateRandomAlias() {
        StringBuilder sb = new StringBuilder(ALIAS_LENGTH);
        for (int i = 0; i < ALIAS_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }

        String alias = sb.toString();

        // Check if the alias already exists, if so, generate a new one
        if (urlMappingRepository.existsById(alias)) {
            return generateRandomAlias(); // Recursive call to try again
        }

        return alias;
    }

    /**
     * Gets the base URL from the HTTP request.
     *
     * @param request The HTTP request
     * @return The base URL
     */
    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        if ((serverPort != 80 && scheme.equals("http")) ||
                (serverPort != 443 && scheme.equals("https"))) {
            url.append(":").append(serverPort);
        }

        url.append(contextPath);
        return url.toString();
    }
}
