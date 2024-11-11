package store.domain.order.controller;

import store.common.controller.BaseController;
import store.common.view.InputView;
import store.common.view.OutputView;
import store.domain.inventory.model.Cart;
import store.domain.inventory.model.CartItem;
import store.domain.inventory.model.Price;
import store.domain.order.model.Order;
import store.domain.order.model.OrderItem;
import store.domain.order.service.OrderService;
import store.domain.order.service.PromotionConfirmCallback;

import java.util.List;

public class OrderController extends BaseController implements PromotionConfirmCallback {

    private final OrderService orderService;

    public OrderController(InputView inputView, OutputView outputView, OrderService orderService) {
        super(inputView, outputView);
        this.orderService = orderService;
    }

    public Order placeOrder(Cart cart) {
        List<OrderItem> orderItems = createOrderItems(cart);
        Price totalPrice = orderService.calculateTotalCartPrice(orderItems);
        return new Order(orderItems, totalPrice);
    }

    private List<OrderItem> createOrderItems(Cart cart) {
        return cart.streamItem()
                .map(orderService::applyPromotion)
                .toList();
    }

    @Override
    public boolean confirmExtraPromotion(CartItem cartItem) {
        return inputView.confirmExtraPromotion(cartItem.name());
    }

    @Override
    public boolean confirmRegularPriceOption(CartItem cartItem, int remainingQuantity) {
        return inputView.confirmRegularPriceOption(cartItem.name(), remainingQuantity);
    }
}
