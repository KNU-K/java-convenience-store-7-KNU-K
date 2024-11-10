package store.common.exception;

public class InvalidOrderException extends IllegalArgumentException {
    public InvalidOrderException(ErrorMessages messages) {
        super(messages.getMessage());
    }
}