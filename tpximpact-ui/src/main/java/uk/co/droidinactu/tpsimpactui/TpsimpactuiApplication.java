package uk.co.droidinactu.tpsimpactui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@SpringBootApplication
public class TpsimpactuiApplication implements CommandLineRunner {

    private static final String SHORTEN_URL_HOST = "http://localhost:8080";
    private static final String SHORTEN_URL_ENDPOINT_SHORTEN = "/shorten";
    private static final String SHORTEN_URL_ENDPOINT_FIND = "/";
    private static final String SHORTEN_URL_ENDPOINT_DELETE = "/";
    private static final String SHORTEN_URL_ENDPOINT_LIST = "/urls";

    private static final Logger LOG = LoggerFactory.getLogger(TpsimpactuiApplication.class);

    public static void main(String[] args) {
        LOG.info("Starting UrlShortener UI Application");
        SpringApplication.run(TpsimpactuiApplication.class, args);
        LOG.info("Finished");
    }

    @Override
    public void run(String... args) {
        LOG.info("Welcome to the TPS Impact UI");
        LOG.info("This application is designed to provide a user interface for the TPS Impact task.");
        LOG.info("Please follow the instructions on the screen to interact with the application.");
        LOG.info("Thank you for using the TPS Impact UI!");

        int userSelected = displayMenu();
        while (userSelected != 99) {
            switch (userSelected) {
                case 1:
                    shortenUrl();
                    break;
                case 2:
                    getShortenedUrl();
                    break;
                case 3:
                    listShortenedUrls();
                    break;
                case 4:
                    deleteShortenedUrl();
                    break;
                default:
                    LOG.warn("Invalid selection. Please try again.");
            }
            userSelected = displayMenu();
        }
    }

    private void deleteShortenedUrl() {
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter the alias you want to delete: ");
        String alias = input.nextLine();

        try {
            Map<String, String> params = new HashMap<String, String>();
            getRestTemplate().delete(SHORTEN_URL_HOST + SHORTEN_URL_ENDPOINT_DELETE + alias, params);
            System.out.println("Shortened URL " + alias + " deleted");
        } catch (Exception e) {
            System.out.println("There was an error deleting the shortened URL: " + alias);
        }
    }

    private void listShortenedUrls() {
        String url = SHORTEN_URL_HOST + SHORTEN_URL_ENDPOINT_LIST;
        try {
            ResponseEntity<UrlMapping[]> response =
                    getRestTemplate().getForEntity(
                            url,
                            UrlMapping[].class);
            UrlMapping[] urls = response.getBody();
            Arrays.stream(urls).toList().forEach(urlMapping -> {
                System.out.println(
                        "Alias: [" + urlMapping.getAlias()
                                + "] - Full URL: [" + urlMapping.getFullUrl()
                                + "] Shortened URL: [" + urlMapping.getShortUrl() + "]");
                System.out.println("-------------------------");
            });
        } catch (Exception e) {
            System.out.println("There was an error listing shortened URLs.");
        }
    }

    private void getShortenedUrl() {
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter the alias you want to find: ");
        String alias = input.nextLine();

        String url = SHORTEN_URL_HOST + SHORTEN_URL_ENDPOINT_FIND + alias;
        try {
            ResponseEntity<String> response =
                    getRestTemplate().getForEntity(
                            url,
                            String.class);
            System.out.println(response.getBody());
        } catch (Exception e) {
            System.out.println("There was an error listing shortened URLs.");
        }
    }

    private void shortenUrl() {
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter the URL you want to shorten: ");
        String fullUrl = input.nextLine();

        System.out.print("Do you want to provide a custom alias? (yes/no): ");
        String yesOrNo = input.nextLine();

        String customAlias = null;
        if (yesOrNo.equalsIgnoreCase("yes")) {
            System.out.print("Enter custom alias?: ");
            customAlias = input.nextLine();
        }

        ShortenUrlRequest shortenUrlRequest =
                ShortenUrlRequest
                        .builder()
                        .fullUrl(fullUrl)
                        .customAlias(customAlias)
                        .build();

        String url = SHORTEN_URL_HOST + SHORTEN_URL_ENDPOINT_SHORTEN;
        try {
            HttpEntity<ShortenUrlRequest> packetEntity = new HttpEntity<>(shortenUrlRequest, getHeaders());
            ResponseEntity<ShortenUrlResponse> result = getRestTemplate().exchange(url, HttpMethod.POST, packetEntity, ShortenUrlResponse.class);
            System.out.println("Shortened URL response: " + result.getBody().getShortUrl());
        } catch (Exception e) {
            System.out.println("There was an error shortening your URL: " + fullUrl);
        }
    }

    public int displayMenu() {
        int selection;
        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.println();
        System.out.println("Choose from these choices");
        System.out.println("-------------------------\n");
        System.out.println("1  - Shorten a URL");
        System.out.println("2  - Retrieve a Shortened URL");
        System.out.println("3  - List all Shortened URLs");
        System.out.println("4  - Delete a Shortened URLs");
        System.out.println("99 - Quit");
        System.out.println();
        System.out.print("Please enter your choice: ");

        selection = input.nextInt();
        return selection;
    }

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(getJsonMessageConverters());
        return restTemplate;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }


    private List<HttpMessageConverter<?>> getJsonMessageConverters() {
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new MappingJackson2HttpMessageConverter());
        return converters;
    }
}