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
import store.domain.promotion.model.PromotionPolicy;

import java.util.List;

public class OrderController extends BaseController {
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
                .map(this::createOrderItemWithPromotion)
                .map(this::confirmAndAdjustRegularPriceQuantity)
                .toList();
    }

    private OrderItem createOrderItemWithPromotion(CartItem cartItem) {
        PromotionPolicy policy = orderService.getPromotionPolicy(cartItem);
        int availablePromotionQuantity = orderService.calculateAvailablePromotionQuantity(cartItem);
        int limitedPromotionQuantity = orderService.calculateLimitedPromotionQuantity(cartItem,policy);
        Price price = orderService.findProductPrice(cartItem.name());
        if (policy == null) {
            return new OrderItem(cartItem, price, 0, null);
        }
        if (orderService.isEligibleForExtraItem(cartItem) && confirmExtraPromotion(cartItem)) {
            cartItem.increaseQuantity();
        }
        return new OrderItem(cartItem, price, Math.min(limitedPromotionQuantity, availablePromotionQuantity), policy);
    }

    private OrderItem confirmAndAdjustRegularPriceQuantity(OrderItem orderItem) {
        if (orderItem.promotionQuantity() == 0) return orderItem;
        if (orderItem.quantity() == orderItem.promotionQuantity()) return orderItem;
        if(!orderService.isValidPromotionQuantity(orderItem.promotionQuantity(), orderItem.promotionPolicy())){
            orderItem.updatePromotionQuantity(0);
            return orderItem;
        }
        int applicableQuantity = orderService.getApplicableQuantity(orderItem);
        int remainingQuantity = orderItem.quantity() - applicableQuantity;
        if (remainingQuantity <= 0) remainingQuantity = orderItem.quantity();
        boolean confirmRegularPrice = confirmRegularPriceOption(orderItem, remainingQuantity);
        orderService.checkSufficientQuantity(orderItem.name(), orderItem.quantity());
        if (!confirmRegularPrice) {
            orderItem.decreaseQuantity(remainingQuantity);
            orderItem.updatePromotionQuantity(applicableQuantity);
        }
        return orderItem;
    }


    private boolean confirmExtraPromotion(CartItem cartItem) {
        return inputView.confirmExtraPromotion(cartItem.name());
    }

    private boolean confirmRegularPriceOption(OrderItem cartItem, int remainingQuantity) {
        return inputView.confirmRegularPriceOption(cartItem.name(), remainingQuantity);
    }
}
