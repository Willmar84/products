package com.capitole.zara.products.adapter.model;

import com.capitole.zara.products.adapter.utils.Utilities;
import com.capitole.zara.products.domain.RateDomain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RateResponseTest {
    public static final double PRICE = 25.45;
    public static final int PRODUCT_ID = 35455;
    public static final int BRAND_ID = 1;
    public static final int RATE_ID = 2;
    public static final String APPLICATION_DATE = "2020-06-14 15:00:00";

    @Test
    @DisplayName("Mpear correctamente de RateDomain a RateResponse")
    void mapDomainToResponseOk() {
        LocalDateTime domainDate = LocalDateTime.of(2020, 6, 14, 15, 0);

        RateDomain domain = RateDomain.builder()
                .productId(PRODUCT_ID)
                .brandId(BRAND_ID)
                .priceList(RATE_ID)
                .price(BigDecimal.valueOf(PRICE))
                .applicationDate(domainDate)
                .build();

        RateResponse response = RateResponse.toResponse(domain, APPLICATION_DATE);

        assertNotNull(response);
        assertEquals(PRODUCT_ID, response.getIdProducto());
        assertEquals(BRAND_ID, response.getIdBrand());
        assertEquals(RATE_ID, response.getPriceList());
        assertEquals(BigDecimal.valueOf(PRICE), response.getPrice());
        assertEquals(Utilities.stringToLocalDateTime(APPLICATION_DATE), response.getDate());
    }
}
