package com.capitole.zara.products.faker;

import com.capitole.zara.products.adapter.persistence.RateEntity;

import java.math.BigDecimal;

public class RateEntityFaker {
    public static RateEntity getRateEntityResponseOk(Long id, Integer productId, Integer idRate, Integer brandId, Integer priority,
                                    BigDecimal price){
        return RateEntity.builder()
                .id(id)
                .productId(productId)
                .idRate(idRate)
                .brandId(brandId)
                .priority(priority)
                .price(price)
                .build();
    }
}
