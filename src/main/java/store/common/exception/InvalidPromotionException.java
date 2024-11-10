package store.common.exception;

public class InvalidPromotionException extends IllegalArgumentException {
    public InvalidPromotionException(ErrorMessages messages) {
        super(messages.getMessage());
    }
}