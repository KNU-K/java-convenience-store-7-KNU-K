package store.mock;

import store.common.handler.InputRetryHandler;
import store.common.view.InputView;
import store.domain.inventory.factory.CartFactory;
import store.domain.inventory.model.Cart;

public class MockInputView  extends InputView {
    private boolean membership;
    private boolean continueShopping;
    private String expectedInput;

    public MockInputView(InputRetryHandler inputRetryHandler) {
        super(inputRetryHandler);
    }

    @Override
    public Cart getProductNameAndQuantity() {
        return CartFactory.createCartFromInput(expectedInput);
    }

    public void setMembership(boolean membership) {
        this.membership = membership;
    }

    @Override
    public boolean confirmMembership() {
        return membership;
    }

    public void setContinueShopping(boolean continueShopping) {
        this.continueShopping = continueShopping;
    }

    @Override
    public boolean getContinueShoppingConfirmation() {
        return continueShopping;
    }

    public void setProductNameAndQuantity(String expectedInput) {
        this.expectedInput = expectedInput;
    }

}