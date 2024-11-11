package store.common.exception;

import store.common.constants.ErrorMessages;

public class InvalidOrderException extends IllegalStateException {
    public InvalidOrderException(ErrorMessages messages) {
        super(messages.getMessage());
    }
}