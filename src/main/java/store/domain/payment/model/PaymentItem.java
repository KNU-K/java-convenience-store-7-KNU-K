package store.domain.payment.model;

import store.domain.inventory.model.Price;

/**
 * @param name                   상품 이름
 * @param quantity               상품 갯수
 * @param giftQuantity           증정품 갯수
 * @param originPrice            초기 금액
 * @param promotionDiscountPrice 프로모션 할인 금액
 */
public record PaymentItem(String name, int quantity, int giftQuantity, Price originPrice,
                          Price promotionDiscountPrice) {
}

