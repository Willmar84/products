package com.capitole.zara.products.adapter.model;

import com.capitole.zara.products.adapter.utils.Utilities;
import com.capitole.zara.products.domain.RateDomain;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class RateResponse {
    Integer idProducto;
    Integer idBrand;
    Integer priceList;
    LocalDateTime date;
    BigDecimal price;

    public static RateResponse toResponse(RateDomain domain, String applicationDate){
        return RateResponse.builder()
                .idProducto(domain.getProductId())
                .idBrand(domain.getBrandId())
                .priceList(domain.getPriceList())
                .date(domain.getApplicationDate())
                .price(domain.getPrice())
                .date(Utilities.stringToLocalDateTime(applicationDate))
                .build();
    }
}
