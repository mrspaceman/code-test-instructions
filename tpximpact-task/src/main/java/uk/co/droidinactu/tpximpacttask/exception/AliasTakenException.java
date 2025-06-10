package uk.co.droidinactu.tpximpacttask.exception;

/**
 * Exception thrown when a custom alias is already taken.
 */
public class AliasTakenException extends RuntimeException {

    /**
     * Constructs a new AliasTakenException with the specified detail message.
     *
     * @param message the detail message
     */
    public AliasTakenException(String message) {
        super(message);
    }
}