package store.extension;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import store.common.initializer.InventoryInitializer;
import store.domain.inventory.model.*;
import store.domain.promotion.model.Promotion;
import store.domain.promotion.model.PromotionPolicy;

import java.time.LocalDate;

public class InventoryExtension implements BeforeEachCallback {
    private static final LocalDate FIXED_DATE = LocalDate.of(2024, 11, 11);
    private Product product1;
    private Product product2;
    private Stock stock;
    private Inventory inventory;

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        InventoryInitializer.getInstance().resetInventory();

        inventory = InventoryInitializer.getInstance().getInventory();

        product1 = new Product("item1", new Price(1000));
        product2 = new Product("item2", new Price(2000));

        stock = new Stock(product1, 10);

        //RegularPromotion 용
        PromotionPolicy promotionPolicy1 = new PromotionPolicy(2, 1, FIXED_DATE.minusDays(1), FIXED_DATE.plusDays(1));  // Example policy
        Promotion promotion1 = new Promotion("item1", promotionPolicy1);
        PromotionStock promotionStock1 = new PromotionStock(product1, 3, promotion1);  // 5 units for promotion stock

        //ExtraQuantityPromotion 용
        PromotionPolicy promotionPolicy2 = new PromotionPolicy(2, 1, FIXED_DATE.minusDays(1), FIXED_DATE.plusDays(1));  // Example policy
        Promotion promotion2 = new Promotion("item2", promotionPolicy2);
        PromotionStock promotionStock2 = new PromotionStock(product2, 5, promotion2);  // 5 units for promotion stock

        inventory.addStock(stock);
        inventory.addPromotionStock(promotionStock1);
        inventory.addPromotionStock(promotionStock2);

    }
}
