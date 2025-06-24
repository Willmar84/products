package com.capitole.zara.products.application.port.out;

import com.capitole.zara.products.domain.RateDomain;

import java.time.LocalDateTime;

public interface RateRepositoryPort {
    RateDomain searchRateByDate(LocalDateTime date, Integer productId, Integer brandId);
}
