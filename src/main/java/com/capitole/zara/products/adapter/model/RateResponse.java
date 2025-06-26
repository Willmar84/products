package com.capitole.zara.products.adapter.model;

import com.capitole.zara.products.adapter.utils.Utilities;
import com.capitole.zara.products.domain.RateDomain;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class RateResponse {
    @JsonProperty("id_producto")
    Integer idProducto;
    @JsonProperty("id_cadena")
    Integer idBrand;
    @JsonProperty("tarifa")
    Integer priceList;
    @JsonProperty("fecha_tarifa")
    LocalDateTime date;
    @JsonProperty("precio_producto")
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
