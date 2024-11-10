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
        return cart.streamItem()
                .map(this::applyExtraPromotion)
                .map(this::confirmAndAdjustRegularPriceQuantity)
                .toList();
    }

    private CartItem applyExtraPromotion(CartItem cartItem) {
        PromotionPolicy policy = orderService.getPromotionPolicy(cartItem);
        PromotionStrategy strategy =  PromotionStrategySelector.select(cartItem, policy);
        if(strategy == null || PromotionStrategySelector.toType(strategy) != PromotionStrategyType.EXTRA_QUANTITY){
            return cartItem;
        }

        if(confirmExtraPromotion(cartItem)){
            strategy.apply(cartItem,policy);
        }

        return cartItem;
    }

    private OrderItem confirmAndAdjustRegularPriceQuantity(CartItem cartItem) {

        PromotionPolicy policy = orderService.getPromotionPolicy(cartItem);
        PromotionStrategy strategy =  PromotionStrategySelector.select(cartItem, policy);
        Price price = orderService.findProductPrice(cartItem.name());
        if(strategy == null || PromotionStrategySelector.toType(strategy) != PromotionStrategyType.REGULAR_PRICE_STRATEGY){
            return new OrderItem(cartItem, price, 0, policy);
        }
        orderService.createOrderItemWithPromotion(cartItem, policy);

        int remainingQuantity =orderService.getRemainingQuantity(cartItem);
        int applicableQuantity=orderService.getApplicableQuantity(cartItem);
        int availablePromotionQuantity = policy.calculateOptimalPromotionQuantity(cartItem);
        if(confirmRegularPriceOption(cartItem, remainingQuantity)){
            strategy.apply(cartItem,policy);
            orderService.checkSufficientQuantity(cartItem.name(), cartItem.quantity());
            return new OrderItem(cartItem, price, availablePromotionQuantity, policy);
        }
        return new OrderItem(cartItem, price, applicableQuantity, policy);
    }


    private boolean confirmExtraPromotion(CartItem cartItem) {
        return inputView.confirmExtraPromotion(cartItem.name());
    }

    private boolean confirmRegularPriceOption(CartItem orderItem, int remainingQuantity) {
        return inputView.confirmRegularPriceOption(orderItem.name(), remainingQuantity);
    }
}
