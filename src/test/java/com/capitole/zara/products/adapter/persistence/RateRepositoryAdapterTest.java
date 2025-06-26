package com.capitole.zara.products.adapter.persistence;

import com.capitole.zara.products.adapter.utils.Utilities;
import com.capitole.zara.products.domain.RateDomain;
import com.capitole.zara.products.domain.exceptions.RateNotFoundException;
import com.capitole.zara.products.faker.RateEntityFaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RateRepositoryAdapterTest {
    private RateJpaRepository rateJpaRepository;
    private RateRepositoryAdapter adapter;

    public static final String APPLICATION_DATE_TEST = "2020-06-14 10:00:00";
    public static final int PRODUCT_ID = 35455;
    public static final int BRAND_ID = 1;

    @BeforeEach
    void setUp() {
        rateJpaRepository = mock(RateJpaRepository.class);
        adapter = new RateRepositoryAdapter(rateJpaRepository);
    }

    @Test
    @DisplayName("Debe devolver la entidad faker que tiene la prioridad mas alta")
    void returnMaxPriorityEntityFaker() {
        LocalDateTime rateDate = Utilities.stringToLocalDateTime(APPLICATION_DATE_TEST);

        RateEntity entity1 = RateEntityFaker.getRateEntityResponseOk(1L, PRODUCT_ID, 2, BRAND_ID, 1,
                BigDecimal.valueOf(25.45));

        RateEntity entity2 = RateEntityFaker.getRateEntityResponseOk(2L, PRODUCT_ID, 1, BRAND_ID, 0,
                BigDecimal.valueOf(35.50));

        when(rateJpaRepository.searchByDateAndProductIdAndBrandId(rateDate, PRODUCT_ID, BRAND_ID))
                .thenReturn(List.of(entity1, entity2));

        RateDomain result = adapter.searchRateByDate(rateDate, PRODUCT_ID, BRAND_ID);

        //Debe retornar la entidad2 porque es la que tiene mayor prioridad para usar su tarifa
        assertEquals(2, result.getPriceList());
        assertEquals(BigDecimal.valueOf(25.45), result.getPrice());
    }

    @Test
    @DisplayName("Debe lanzar RateNotFoundException cuando no hay resultados ya que todos los productos deben tener " +
            " una tarifa para su venta")
    void throwRateNotFoundExceptionWhenNoRateProduct() {
        LocalDateTime date = LocalDateTime.now();
        when(rateJpaRepository.searchByDateAndProductIdAndBrandId(any(), any(), any()))
                .thenReturn(List.of());

        assertThrows(RateNotFoundException.class,
                () -> adapter.searchRateByDate(date, 1, 1));
    }
}
