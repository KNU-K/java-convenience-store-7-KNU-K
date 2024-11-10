package store.common.exception;

public class InvalidPaymentException extends IllegalStateException {
    public InvalidPaymentException(ErrorMessages messages) {
        super(messages.getMessage());
    }
}