package uk.co.droidinactu.tpximpacttask.exception;

/**
 * Exception thrown when an alias is not found.
 */
public class AliasNotFoundException extends RuntimeException {

    /**
     * Constructs a new AliasNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public AliasNotFoundException(String message) {
        super(message);
    }
}