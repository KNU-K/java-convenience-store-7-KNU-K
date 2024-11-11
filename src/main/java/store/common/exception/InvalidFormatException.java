package store.common.exception;

import store.common.constants.ErrorMessages;

public class InvalidFormatException extends IllegalArgumentException {
    public InvalidFormatException(ErrorMessages messages) {
        super(messages.getMessage());
    }
}
