package store.common.exception;

public class InvalidOrderException extends IllegalStateException {
    public InvalidOrderException(ErrorMessages messages) {
        super(messages.getMessage());
    }
}