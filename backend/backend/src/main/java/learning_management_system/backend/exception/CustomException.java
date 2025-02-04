package learning_management_system.backend.exception;

/**
 * Base class for all custom exceptions in the application.
 */
public class CustomException extends RuntimeException {
    private final String errorCode;

    public CustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
