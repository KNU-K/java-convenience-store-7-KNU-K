package store.common.exception;

public class InvalidInventoryException extends IllegalArgumentException {
    public InvalidInventoryException(ErrorMessages messages) {
        super(messages.getMessage());
    }
}