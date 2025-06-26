package com.capitole.zara.products.faker;

import com.capitole.zara.products.adapter.utils.Utilities;
import com.capitole.zara.products.domain.RateDomain;

import java.math.BigDecimal;

public class RateDomainFaker {
    public static final String APPLICATION_DATE_TEST = "2020-06-14 10:00:00";

    public static RateDomain getTariffDomainTestOk(){
        return RateDomain.builder()
                .price(BigDecimal.valueOf(35.50))
                .brandId(1)
                .productId(35455)
                .applicationDate(Utilities.stringToLocalDateTime(APPLICATION_DATE_TEST))
                .build();
    }
}
