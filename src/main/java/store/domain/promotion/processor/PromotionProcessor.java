package store.domain.promotion.processor;

import store.domain.promotion.factory.PromotionFactory;
import store.domain.promotion.model.PromotionPolicy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PromotionProcessor {

    private final PromotionFactory promotionFactory;

    public PromotionProcessor(PromotionFactory promotionFactory) {
        this.promotionFactory = promotionFactory;
    }

    public void processPromotionDetails(String[] promotionDetails) {
        String promotionName = promotionDetails[0];
        int buyQuantity = Integer.parseInt(promotionDetails[1]);
        int getQuantity = Integer.parseInt(promotionDetails[2]);

        LocalDate startDate = LocalDate.parse(promotionDetails[3], DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endDate = LocalDate.parse(promotionDetails[4], DateTimeFormatter.ISO_LOCAL_DATE);

        PromotionPolicy promotionPolicy = new PromotionPolicy(buyQuantity, getQuantity, startDate, endDate);
        promotionFactory.addPolicy(promotionName, promotionPolicy);
    }
}
