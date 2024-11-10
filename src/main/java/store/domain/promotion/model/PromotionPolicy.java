package store.domain.promotion.model;

import camp.nextstep.edu.missionutils.DateTimes;
import store.common.exception.ErrorMessages;
import store.common.exception.InvalidPromotionException;
import store.common.initializer.InventoryInitializer;
import store.domain.inventory.model.CartItem;
import store.domain.inventory.model.Inventory;

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
    public int getApplicableQuantity(int limitedQuantity) {
        return getGiftQuantity(limitedQuantity) * getTotalQuantityRequiredForPromotion();
    }

    public boolean isValidPromotionQuantity(int promotionQuantity) {
        return promotionQuantity >= buyQuantity;
    }

    public int calculateOptimalPromotionQuantity(CartItem cartItem) {
        Inventory inventory = InventoryInitializer.getInstance().getInventory();
        inventory.getPromotionStockCount(cartItem.name());
        if(cartItem.quantity() >= cartItem.quantity()){
            return cartItem.quantity();
        }
        int remainder = buyQuantity % getTotalQuantityRequiredForPromotion();
        int addition = remainder / getQuantity;
        return cartItem.quantity() + addition;
    }

}
