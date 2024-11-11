package store.common.exception;

import store.common.constants.ErrorMessages;

public class InvalidPromotionException extends IllegalStateException {
    public InvalidPromotionException(ErrorMessages messages) {
        super(messages.getMessage());
    }
}