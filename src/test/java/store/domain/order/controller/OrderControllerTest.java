package store.domain.order.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import store.common.view.InputView;
import store.common.view.OutputView;
import store.domain.inventory.model.Cart;
import store.domain.inventory.model.CartItem;
import store.domain.inventory.model.Price;
import store.domain.order.model.Order;
import store.domain.order.service.OrderService;
import store.extension.DataExtension;

import store.mock.MockInventoryService;
import store.mock.MockPromotionConfirmCallback;
import store.mock.MockPromotionService;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DataExtension.class)
class OrderControllerTest {

    private OrderController orderController;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockInventoryService inventoryService = new MockInventoryService();
        MockPromotionService promotionService = new MockPromotionService();
        MockPromotionConfirmCallback promotionCallback = new MockPromotionConfirmCallback();
        InputView inputView = new InputView(null);
        OutputView outputView = new OutputView();
        orderService = new OrderService(inventoryService, promotionService, promotionCallback);
        orderController = new OrderController(inputView, outputView, orderService);
    }

    @Test
    void 카트의_상품으로_주문을_생성하고_총_가격을_계산한다() {
        // Given
        Cart cart = new Cart();
        cart.addItem(new CartItem("콜라", 3));
        // When
        Order order = orderController.placeOrder(cart);

        // Then
        assertThat(order).isNotNull();
        assertThat(order.totalPrice()).isEqualTo(new Price(3000)); // 상품당 가격이 100이라고 가정
        assertThat(order.orderItems()).hasSize(1);
        assertThat(order.orderItems().get(0).name()).isEqualTo("콜라");
    }
}
