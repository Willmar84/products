package com.capitole.zara.products.adapter;

import com.capitole.zara.products.adapter.persistence.RateJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Pruebas unitarias  Tarifas Productos Zara de acuerdo a fechas de peticion")
@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/data-test.sql")
@TestPropertySource(locations = "classpath:application-test.properties")
public class RateControllerTest {

    public static final int PRODUCT_ID = 35455;
    public static final int BRAND_ID = 1;
    public static final String URL_GET_RATE = "/api/rate";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RateJpaRepository rateJpaRepository;

    @Test
    @DisplayName("Prueba numero 1 para una petición a las 10:00 del día 14 del producto 35455   para la brand 1 (ZARA), " +
            "donde se espera que el precio del producto sea 35.50 ya que solo hay una tarifa disponible para esa fecha")
    public void test1() throws Exception {
        getRateProduct("2020-06-14 10:00:00", BigDecimal.valueOf(35.50));
    }

    @Test
    @DisplayName("Prueba numero 2 para una petición a las 16:00 del día 14 del producto 35455   para la brand 1 (ZARA)," +
            "donde se espera que el precio del producto sea 25.45 porque solamente hay una tarifa para la fecha ingresada")
    public void test2() throws Exception {
        getRateProduct("2020-06-14 16:00:00", BigDecimal.valueOf(25.45));
    }

    @Test
    @DisplayName("Prueba numero 3 para una petición a las 21:00 del día 14 del producto 35455   para la brand 1 (ZARA)," +
            "donde se espera que el precio del producto sea 35.50 porque para la fecha ingresada solo se tiene la tarifa 1(PriceList)")
    public void test3() throws Exception {
        getRateProduct("2020-06-14 21:00:00", BigDecimal.valueOf(35.50));
    }

    @Test
    @DisplayName("Prueba numero 4 para una petición a las 10:00 del día 15 del producto 35455   para la brand 1 (ZARA), " +
            "donde se espera que el precio del producto sea 30.50 porque la tarifa para la fecha ingresada es la 3(PriceList)")
    public void test4() throws Exception {
        getRateProduct("2020-06-15 10:00:00", BigDecimal.valueOf(30.50));
    }

    @Test
    @DisplayName("Prueba numero 5 para una petición a las 21:00 del día 16 del producto 35455   para la brand 1 (ZARA)," +
            " donde se espera que el precio del producto sea 38.95, ya para el caso de esta peticion hecha el " +
            "2020-06-16 21:00:00 hay dos posibles tarifas(PriceList) la 1 o la 4, pero al ocurrir esto el sistema" +
            "debe tomar como medio de eleccion la tarifa que tenga el mayor valor en el campo priority," +
            "que para la tarifa 1 es el valor 0 y para la tarifa 4 es el valor 1, por lo tanto la tarifa 4 que tiene" +
            "la prioridad mas alta es la que define el valor del producto el cual es 38.95")
    public void test5() throws Exception {
        getRateProduct("2020-06-16 21:00:00", BigDecimal.valueOf(38.95));
    }

    private void getRateProduct(String applicationDate, BigDecimal expectedPrice) throws Exception {

        mockMvc.perform(get(URL_GET_RATE)
                        .param("fecha_aplicacion", applicationDate)
                        .param("id_producto", String.valueOf(PRODUCT_ID))
                        .param("id_cadena", String.valueOf(BRAND_ID))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_producto").value(PRODUCT_ID))
                .andExpect(jsonPath("$.id_cadena").value(BRAND_ID))
                .andExpect(jsonPath("$.precio_producto").value(expectedPrice));
    }
}
