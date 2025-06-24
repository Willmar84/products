package com.capitole.zara.products.domain;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
public class RateDomain {
    Integer productId;
    Integer brandId;
    Integer priceList;
    LocalDateTime applicationDate;
    BigDecimal price;
}
