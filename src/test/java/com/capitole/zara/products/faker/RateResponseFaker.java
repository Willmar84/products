package com.capitole.zara.products.faker;

import com.capitole.zara.products.adapter.model.RateResponse;
import com.capitole.zara.products.adapter.utils.Utilities;
import java.math.BigDecimal;

public class RateResponseFaker {
    public static final String APPLICATION_DATE_TEST = "2020-06-14 10:00:00";

    public static RateResponse getTariffResponseOk(){
        return RateResponse.builder()
                .idProducto(35455)
                .idBrand(1)
                .priceList(1)
                .date(Utilities.stringToLocalDateTime(APPLICATION_DATE_TEST))
                .price(BigDecimal.valueOf(35.50))
                .build();
    }
}
