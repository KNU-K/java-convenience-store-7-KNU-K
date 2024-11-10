package store.domain.order.controller;

import store.common.controller.BaseController;
import store.common.view.InputView;
import store.common.view.OutputView;
import store.domain.inventory.model.Cart;
import store.domain.inventory.model.CartItem;
import store.domain.inventory.model.Price;
import store.domain.order.factory.OrderFactory;
import store.domain.order.model.Order;
import store.domain.order.model.OrderItem;
import store.domain.order.service.OrderService;
import store.domain.promotion.factory.PromotionStrategySelector;
import store.domain.promotion.model.PromotionPolicy;
import store.domain.promotion.strategy.PromotionStrategy;
import store.domain.promotion.strategy.PromotionStrategyType;

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
        return cart.streamItem().map(this::applyPromotion).toList();
    }

    private OrderItem applyPromotion(CartItem cartItem) {
        PromotionPolicy policy = orderService.getPromotionPolicy(cartItem);
        Price price = orderService.findProductPrice(cartItem.name());
        PromotionStrategy strategy = PromotionStrategySelector.select(cartItem, policy);
        if (isValidPromotion(policy, strategy) && PromotionStrategySelector.toType(strategy) == PromotionStrategyType.EXTRA_QUANTITY) {
            return applyExtraPromotion(cartItem, price, policy);
        }
        if (isValidPromotion(policy, strategy) && PromotionStrategySelector.toType(strategy) == PromotionStrategyType.REGULAR_PRICE_STRATEGY) {
            return applyRegularPromotion(cartItem, price, policy);
        }
        if (policy != null && policy.isValidDate()) {
            orderService.checkSufficientQuantity(cartItem.name(), cartItem.quantity());
            return createOrderItem(cartItem, price, orderService.getApplicableQuantity(cartItem), policy);
        }
        return createOrderItem(cartItem, price, 0, policy);
    }

    private OrderItem applyExtraPromotion(CartItem cartItem, Price price, PromotionPolicy policy) {
        int promotionQuantity = policy.calculateExtraPromotionQuantity(cartItem);

        if (confirmExtraPromotion(cartItem)) {
            orderService.applyPromotionStrategy(cartItem, policy);
        }

        return createOrderItem(cartItem, price, promotionQuantity, policy);
    }

    private OrderItem applyRegularPromotion(CartItem cartItem, Price price, PromotionPolicy policy) {
        int applicableQuantity = orderService.getApplicableQuantity(cartItem);
        int availablePromotionQuantity = orderService.getApplicableQuantity(cartItem);
        int remainingQuantity = orderService.getRemainingQuantity(cartItem);

        if (availablePromotionQuantity == 0) {
            return createOrderItem(cartItem, price, 0, policy);
        }
        if (cartItem.quantity() == availablePromotionQuantity) {
            return createOrderItem(cartItem, price, policy.calculateExtraPromotionQuantity(cartItem), policy);
        }
        if (!orderService.isPromotionQuantityValid(availablePromotionQuantity, policy)) {
            return createOrderItem(cartItem, price, 0, policy);
        }
        if (!confirmRegularPriceOption(cartItem, remainingQuantity)) {
            orderService.applyPromotionStrategy(cartItem, policy);
        }
        return createOrderItem(cartItem, price, applicableQuantity, policy);
    }


    private OrderItem createOrderItem(CartItem cartItem, Price price, int promotionQuantity, PromotionPolicy promotionPolicy) {
        return OrderFactory.createOrderItem(cartItem, price, promotionQuantity, promotionPolicy);
    }

    private boolean isValidPromotion(PromotionPolicy policy, PromotionStrategy strategy) {
        return policy != null && strategy != null;
    }

    private boolean confirmExtraPromotion(CartItem cartItem) {
        return inputView.confirmExtraPromotion(cartItem.name());
    }

    private boolean confirmRegularPriceOption(CartItem orderItem, int remainingQuantity) {
        return inputView.confirmRegularPriceOption(orderItem.name(), remainingQuantity);
    }
}
