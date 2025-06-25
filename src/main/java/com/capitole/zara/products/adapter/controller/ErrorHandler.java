package com.capitole.zara.products.adapter.controller;

import com.capitole.zara.products.domain.exceptions.RateNotFoundException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

public class ErrorHandler {
    private final HttpServletRequest httpServletRequest;

    public ErrorHandler(final HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @ExceptionHandler(RateNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleError(RateNotFoundException ex){
        return getApiResponseError(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleError(Exception ex){
        return getApiResponseError(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    /*
    Se arma el objeto apiResponseError  a partir del httpStatus que queramos retornar segun
    el error generado y la excepcion recibida, definiendo un comportamiento generico en caso de que
    se quieran manejar otros tipos de Errores, con lo cual se pueden definir una sobrecarga de parametros
     para el metodo hadleError y asi poder manejar otras excepciones diferentes pero manejando una respuesta
     generica con el getApiResponseError
     */
    private ResponseEntity<ApiErrorResponse> getApiResponseError(HttpStatus httpStatus, Throwable ex){
        ApiErrorResponse apiErrorResponse = ApiErrorResponse
                .builder()
                .status(httpStatus.value())
                .timestamp(LocalDateTime.now())
                .resource(httpServletRequest.getRequestURI())
                .detail(String.format("%s: %s", ex.getClass().getCanonicalName(), ex.getMessage()))
                .build();
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);
    }


    @Builder
    private static class ApiErrorResponse{
        private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss[.SSSSSSSSS]['Z']";

        @JsonProperty
        private Integer status;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
        private LocalDateTime timestamp;
        @JsonProperty
        private String resource;
        @JsonProperty
        private String detail;
    }
}
