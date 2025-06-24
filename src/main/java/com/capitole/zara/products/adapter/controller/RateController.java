package com.capitole.zara.products.adapter.controller;

import com.capitole.zara.products.adapter.model.RateResponse;
import com.capitole.zara.products.application.port.in.SearchRateUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rate")
public class RateController {
    private final SearchRateUseCase searchRateUseCase;

    public RateController(SearchRateUseCase searchRateUseCase) {
        this.searchRateUseCase = searchRateUseCase;
    }

    @GetMapping()
    public RateResponse getRate(@RequestParam(value = "fecha_aplicacion") String applicationDate,
                                @RequestParam(value = "id_producto") Integer productId,
                                @RequestParam(value = "id_cadena") Integer brandId){
        return RateResponse.toResponse(searchRateUseCase.execute(applicationDate, productId, brandId), applicationDate);
    }
}
