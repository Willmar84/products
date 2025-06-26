package com.capitole.zara.products.adapter;

import com.capitole.zara.products.adapter.controller.RateController;
import com.capitole.zara.products.adapter.model.RateResponse;
import com.capitole.zara.products.application.port.in.SearchRateUseCase;
import com.capitole.zara.products.faker.RateDomainFaker;
import com.capitole.zara.products.faker.RateResponseFaker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Prueba unitaria Controlador Tarifas Productos Zara")
@WebMvcTest(RateController.class)
public class RateControllerTest {

    public static final String APPLICATION_DATE_TEST = "2020-06-14 10:00:00";
    public static final int PRODUCT_ID = 35455;
    public static final double PRICE_PRODUCT = 35.50;
    public static final int BRAND_ID = 1;
    public static final String URL_GET_RATE = "/api/rate";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchRateUseCase searchRateUseCase;

    @Test
    @DisplayName("Test obteniendo tarifa y precio de producto")
    void getRateProduct() throws Exception {
        var domainResult = RateDomainFaker.getTariffDomainTestOk();
        var expectedResponse = RateResponseFaker.getTariffResponseOk();

        when(searchRateUseCase.execute(APPLICATION_DATE_TEST, PRODUCT_ID, BRAND_ID))
                .thenReturn(domainResult);

        Mockito.mockStatic(RateResponse.class)
                .when(() -> RateResponse.toResponse(domainResult, APPLICATION_DATE_TEST))
                .thenReturn(expectedResponse);

        mockMvc.perform(get(URL_GET_RATE)
                        .param("fecha_aplicacion", APPLICATION_DATE_TEST)
                        .param("id_producto", String.valueOf(PRODUCT_ID))
                        .param("id_cadena", String.valueOf(BRAND_ID))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_producto").value(PRODUCT_ID))
                .andExpect(jsonPath("$.id_cadena").value(BRAND_ID))
                .andExpect(jsonPath("$.precio_producto").value(PRICE_PRODUCT));
    }
}
