package com.capitole.zara.products.application.port.in;

import com.capitole.zara.products.domain.RateDomain;

public interface SearchRateUseCase {
    RateDomain execute(String applicationDate, Integer productId, Integer brandId);
}
