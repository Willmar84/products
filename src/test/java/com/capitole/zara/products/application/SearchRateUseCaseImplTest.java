package com.capitole.zara.products.application;

import com.capitole.zara.products.adapter.utils.Utilities;
import com.capitole.zara.products.application.port.out.RateRepositoryPort;
import com.capitole.zara.products.application.usecase.SearchRateUseCaseImpl;
import com.capitole.zara.products.domain.RateDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class SearchRateUseCaseImplTest {
    public static final String APPLICATION_DATE = "2020-06-14 10:00:00";
    public static final int PRODUCT_ID = 35455;
    public static final int BRAND_ID = 1;
    public static final double PRICE = 25.45;
    public static final int RATE_ID = 2;
    private RateRepositoryPort rateRepositoryPort;
    private SearchRateUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        rateRepositoryPort = mock(RateRepositoryPort.class);
        useCase = new SearchRateUseCaseImpl(rateRepositoryPort);
    }

    @Test
    @DisplayName("Debe devolver RateDomain correctamente usando parámetros válidos")
    void shouldReturnRateDomainGivenValidParams() {
        LocalDateTime aplicationDate = Utilities.stringToLocalDateTime(APPLICATION_DATE);
        RateDomain expectedRate = RateDomain.builder()
                .productId(PRODUCT_ID)
                .brandId(BRAND_ID)
                .priceList(RATE_ID)
                .price(BigDecimal.valueOf(PRICE))
                .applicationDate(aplicationDate)
                .build();

        when(rateRepositoryPort.searchRateByDate(aplicationDate, PRODUCT_ID, BRAND_ID))
                .thenReturn(expectedRate);

        RateDomain result = useCase.execute(APPLICATION_DATE, PRODUCT_ID, BRAND_ID);

        assertNotNull(result);
        assertEquals(expectedRate, result);
        verify(rateRepositoryPort).searchRateByDate(aplicationDate, PRODUCT_ID, BRAND_ID);
    }
}
