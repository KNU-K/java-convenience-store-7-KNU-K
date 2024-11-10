package store.common.exception;

public class InvalidPaymentException extends IllegalArgumentException {
    public InvalidPaymentException(ErrorMessages messages) {
        super(messages.getMessage());
    }
}