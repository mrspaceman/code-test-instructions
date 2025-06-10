package uk.co.droidinactu.tpximpacttask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application.
 * Provides consistent error responses for various exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles IllegalArgumentException.
     * Used for business logic validation errors like "alias already taken" or "alias not found".
     *
     * @param ex The exception
     * @return A response entity with a 400 Bad Request status and error message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles AliasTakenException.
     * Used when a custom alias is already taken.
     *
     * @param ex The exception
     * @return A response entity with a 400 Bad Request status and error message
     */
    @ExceptionHandler(AliasTakenException.class)
    public ResponseEntity<Map<String, String>> handleAliasTakenException(AliasTakenException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles AliasNotFoundException.
     * Used when an alias is not found.
     *
     * @param ex The exception
     * @return A response entity with a 404 Not Found status and error message
     */
    @ExceptionHandler(AliasNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleAliasNotFoundException(AliasNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles validation errors from @Valid annotations.
     * Used for request body validation errors.
     *
     * @param ex The exception
     * @return A response entity with a 400 Bad Request status and field-specific error messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all other exceptions.
     *
     * @param ex The exception
     * @return A response entity with a 500 Internal Server Error status and error message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "An unexpected error occurred: " + ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
