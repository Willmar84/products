package com.capitole.zara.products.adapter.persistence;

import com.capitole.zara.products.application.port.out.RateRepositoryPort;
import com.capitole.zara.products.domain.RateDomain;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;

@Component
@AllArgsConstructor
public class RateRepositoryAdapter implements RateRepositoryPort {
    private RateJpaRepository tariffJpaRepository;

    @Override
    public RateDomain searchRateByDate(LocalDateTime date, Integer productId, Integer brandId) {
        return tariffJpaRepository.searchByDateAndProductIdAndBrandId(date, productId, brandId).stream()
                .map(t -> RateDomain.builder()
                        .productId(t.getProductId())
                        .brandId(t.getBrandId())
                        .priceList(t.getIdRate())
                        .price(t.getPrice())
                        .build())
                .max(Comparator.comparing(RateDomain::getPriceList))
                .orElseThrow(() -> new RuntimeException("No se encontró tarifa asociada"));
    }
}
