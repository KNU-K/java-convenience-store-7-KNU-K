package store.common.exception;

public class InvalidFileException extends IllegalArgumentException {
    public InvalidFileException(ErrorMessages messages) {
        super(messages.getMessage());
    }
}
