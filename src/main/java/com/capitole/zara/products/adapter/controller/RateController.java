package com.capitole.zara.products.adapter.controller;

import com.capitole.zara.products.adapter.model.RateResponse;
import com.capitole.zara.products.application.port.in.SearchRateUseCase;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/rate")
public class RateController {
    private final SearchRateUseCase searchRateUseCase;

    public RateController(SearchRateUseCase searchRateUseCase) {
        this.searchRateUseCase = searchRateUseCase;
    }

    @GetMapping()
    public RateResponse getRate(@RequestParam(value = "fecha_aplicacion")
                                @NotBlank(message = "La fecha de aplicación es obligatoria")
                                    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$",
                                            message = "Formato de fecha inválido, use yyyy-MM-dd HH:mm")
                                    String applicationDate,
                                @RequestParam(value = "id_producto")
                                @Min(value = 1, message = "El ID del producto debe ser mayor que 0")
                                Integer productId,
                                @RequestParam(value = "id_cadena")
                                @Min(value = 1, message = "El ID de la cadena debe ser mayor que 0")
                                Integer brandId){
        return RateResponse.toResponse(searchRateUseCase.execute(applicationDate, productId, brandId), applicationDate);
    }
}
