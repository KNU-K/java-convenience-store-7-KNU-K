package store.domain.promotion.model;

import camp.nextstep.edu.missionutils.DateTimes;
import store.common.exception.ErrorMessages;
import store.common.exception.InvalidPromotionException;

import java.time.LocalDate;

public class PromotionPolicy {

    private static final int MIN_QUANTITY = 0;
    private final int buyQuantity;
    private final int getQuantity;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public PromotionPolicy(int buyQuantity, int getQuantity, LocalDate startDate, LocalDate endDate) {
        if (buyQuantity <= MIN_QUANTITY || getQuantity < MIN_QUANTITY) {
            throw new InvalidPromotionException(ErrorMessages.NEGATIVE_QUANTITY);
        }
        if (startDate == null || endDate == null) {
            throw new InvalidPromotionException(ErrorMessages.INVALID_PROMOTION);
        }
        if (endDate.isBefore(startDate)) {
            throw new InvalidPromotionException(ErrorMessages.INVALID_DATE);
        }

        this.buyQuantity = buyQuantity;
        this.getQuantity = getQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isValidDate() {
        LocalDate currentDate = DateTimes.now().toLocalDate();  // `LocalDate.now()` 대체
        return !currentDate.isBefore(startDate) && !currentDate.isAfter(endDate);
    }

    public int getTotalQuantityRequiredForPromotion() {
        return buyQuantity + getQuantity;
    }

    public int getGiftQuantity(int purchasedQuantity) {
        System.out.println(purchasedQuantity);
        validateQuantity(purchasedQuantity);
        return purchasedQuantity / getTotalQuantityRequiredForPromotion();
    }

    public boolean isEligibleForExtraItem(int quantity) {
        validateQuantity(quantity);
        return (quantity % getTotalQuantityRequiredForPromotion()) == buyQuantity;
    }

    private void validateQuantity(int quantity) {
        if (quantity < MIN_QUANTITY) {
            throw new InvalidPromotionException(ErrorMessages.NEGATIVE_QUANTITY);
        }
    }

    public int getApplicableQuantityIncludedGift(int quantity) {
        return getGiftQuantity(quantity) * getTotalQuantityRequiredForPromotion();
    }

    public int getApplicableQuantity(int limitedQuantity) {
        return getGiftQuantity(limitedQuantity) * getTotalQuantityRequiredForPromotion();
    }

    public int getApplicableQuantityOfGift(int quantity) {
        return quantity/ getTotalQuantityRequiredForPromotion();
    }
}
