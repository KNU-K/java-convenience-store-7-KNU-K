package store.common.exception;

import store.common.constants.ErrorMessages;

public class InvalidInventoryException extends IllegalArgumentException {
    public InvalidInventoryException(ErrorMessages messages) {
        super(messages.getMessage());
    }
}