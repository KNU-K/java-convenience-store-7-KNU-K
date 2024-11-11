package store.domain.order.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.inventory.model.CartItem;
import store.domain.inventory.model.Price;
import store.domain.promotion.model.PromotionPolicy;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemTest {

    private CartItem cartItem;
    private Price price;
    private PromotionPolicy promotionPolicy;
    private int promotionQuantity;

    @BeforeEach
    void setUp() {
        cartItem = new CartItem("Test Item", 5);
        price = new Price(100);
        promotionPolicy = new PromotionPolicy(2, 1, LocalDate.of(2024, 11, 7), LocalDate.of(2024, 12, 25));
        promotionQuantity = 2;
    }

    @Test
    void 주문_항목이_CartItem으로_생성될_때_필드가_정상적으로_설정된다() {
        // Given
        OrderItem orderItem = new OrderItem(cartItem, price, promotionQuantity, promotionPolicy);

        // Then
        assertThat(orderItem.name()).isEqualTo("Test Item");
        assertThat(orderItem.price()).isEqualTo(price);
        assertThat(orderItem.quantity()).isEqualTo(cartItem.quantity());
        assertThat(orderItem.promotionQuantity()).isEqualTo(promotionQuantity);
        assertThat(orderItem.promotionPolicy()).isEqualTo(promotionPolicy);
    }
}
