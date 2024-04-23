package co.udea.airline.api.utils.exception;

/**
 * Exception when any resource or data is not found.
 */
public class DataNotFoundException extends GeneralRuntimeException {

    private static final long serialVersionUID = 1L;

    public DataNotFoundException(String message) {
        super(message);
    }

}
