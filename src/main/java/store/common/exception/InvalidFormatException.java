package store.common.exception;

public class InvalidFormatException extends IllegalArgumentException {
    public InvalidFormatException(ErrorMessages messages) {
        super(messages.getMessage());
    }
}
