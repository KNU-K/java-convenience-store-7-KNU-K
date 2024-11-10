package store.domain.order.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.inventory.model.Price;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderTest {

    private Order order;
    private Price totalPrice;
    private OrderItem orderItem1;
    private OrderItem orderItem2;

    @BeforeEach
    void setUp() {
        totalPrice = new Price(5000);
        orderItem1 = new OrderItem("상품1",   Price.of(2000), 4,2, null);
        orderItem2 = new OrderItem("상품2", Price.of(1000),  5,3,null);
        List<OrderItem> orderItems = Arrays.asList(orderItem1, orderItem2);
        order = new Order(orderItems, totalPrice);
    }

    @Test
    void 주문_생성() {
        assertNotNull(order);
        assertEquals(2, order.streamOrderItems().count());
        assertEquals(totalPrice, order.totalPrice());
    }

    @Test
    void totalPrice() {
        assertEquals(totalPrice, order.totalPrice());
    }
}

