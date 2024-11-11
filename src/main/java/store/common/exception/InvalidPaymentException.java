package store.common.exception;

import store.common.constants.ErrorMessages;

public class InvalidPaymentException extends IllegalStateException {
    public InvalidPaymentException(ErrorMessages messages) {
        super(messages.getMessage());
    }
}