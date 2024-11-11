package store.common.initializer;

import store.common.constants.FileConstants;
import store.common.utility.FileReader;
import store.domain.inventory.model.Inventory;
import store.domain.inventory.processor.InventoryProcessor;
import store.domain.promotion.factory.PromotionFactory;
import store.domain.promotion.processor.PromotionProcessor;

import java.util.List;

public class DataInitializer {
    private final Inventory inventory = InventoryInitializer.getInstance().getInventory();
    private final PromotionFactory promotionFactory = new PromotionFactory();
    private final PromotionProcessor promotionProcessor = new PromotionProcessor(promotionFactory);
    private final InventoryProcessor inventoryProcessor = new InventoryProcessor(inventory, promotionFactory);

    public void initializeData() {
        List<String[]> promotionsData = FileReader.readMarkdownFile(FileConstants.PROMOTIONS_FILE_PATH.getType());
        promotionsData.forEach(promotionProcessor::processPromotionDetails);

        List<String[]> productsData = FileReader.readMarkdownFile(FileConstants.PRODUCTS_FILE_PATH.getType());
        productsData.forEach(inventoryProcessor::processStockItem);
    }

}
