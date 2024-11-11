package store.domain.inventory.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


public class PriceTest {

    @Test
    void 가격_문자열로_변환() {
        Price price = Price.of(1000);
        assertEquals("1,000", price.toString());
    }

    @Test
    void 가격_곱하기() {
        Price price = Price.of(1000);
        Price result = price.multiply(2);
        assertEquals("2,000", result.toString());
    }

    @Test
    void 비율_적용() {
        Price price = Price.of(1000);
        Price result = price.applyPercentage(10);
        assertEquals("100", result.toString());
    }

    @Test
    void 가격_더하기() {
        Price price1 = Price.of(1000);
        Price price2 = Price.of(500);
        Price result = price1.plus(price2);
        assertEquals("1,500", result.toString());
    }

    @Test
    void 가격_빼기() {
        Price price1 = Price.of(1000);
        Price price2 = Price.of(500);
        Price result = price1.subtract(price2);
        assertEquals("500", result.toString());
    }

    @Test
    void 제로_가격() {
        assertEquals("0", Price.ZERO.toString());
    }


}
