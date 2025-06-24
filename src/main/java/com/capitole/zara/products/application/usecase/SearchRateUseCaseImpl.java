package com.capitole.zara.products.application.usecase;

import com.capitole.zara.products.adapter.utils.Utilities;
import com.capitole.zara.products.application.port.in.SearchRateUseCase;
import com.capitole.zara.products.application.port.out.RateRepositoryPort;
import com.capitole.zara.products.domain.RateDomain;
import org.springframework.stereotype.Component;

@Component
public class SearchRateUseCaseImpl implements SearchRateUseCase {
    private final RateRepositoryPort rateRepositoryPort;

    public SearchRateUseCaseImpl(RateRepositoryPort rateRepositoryPort){
        this.rateRepositoryPort = rateRepositoryPort;
    }

    @Override
    public RateDomain execute(String applicationDate, Integer productId, Integer brandId) {
        return rateRepositoryPort.searchRateByDate(Utilities.stringToLocalDateTime(applicationDate),
                productId, brandId);
    }
}
