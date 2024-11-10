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

    public void processPromotion(String[] parts) {
        String name = parts[0];
        int buy = Integer.parseInt(parts[1]);
        int get = Integer.parseInt(parts[2]);

        LocalDate startDate = LocalDate.parse(parts[3], DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endDate = LocalDate.parse(parts[4], DateTimeFormatter.ISO_LOCAL_DATE);

        PromotionPolicy policy = new PromotionPolicy(buy, get, startDate, endDate);
        promotionFactory.addPolicy(name, policy);
    }
}

