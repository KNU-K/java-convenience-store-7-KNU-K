package store.common.exception;

import store.common.constants.ErrorMessages;

public class InvalidFileException extends IllegalArgumentException {
    public InvalidFileException(ErrorMessages messages) {
        super(messages.getMessage());
    }
}
