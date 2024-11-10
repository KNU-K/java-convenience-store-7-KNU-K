package store.common.exception;

public class InvalidPromotionException extends IllegalStateException {
    public InvalidPromotionException(ErrorMessages messages) {
        super(messages.getMessage());
    }
}