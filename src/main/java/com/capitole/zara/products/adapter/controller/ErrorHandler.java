package com.capitole.zara.products.adapter.controller;

import com.capitole.zara.products.domain.exceptions.RateNotFoundException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandler {
    private final HttpServletRequest httpServletRequest;

    public ErrorHandler(final HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @ExceptionHandler(RateNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleError(RateNotFoundException ex){
        return getApiResponseError(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        Map<String, String> errors = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage,
                        (existing, replacement) -> existing
                ));

        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .detail("Validacion fallida")
                .timestamp(LocalDateTime.now())
                .errors(errors)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingParams(
            MissingServletRequestParameterException ex,
            HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getParameterName(), "El parametro es requerido");

        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .detail("Faltan parametros requeridos en la solicitud")
                .timestamp(LocalDateTime.now())
                .resource(request.getRequestURI())
                .errors(errors)
                .build();

        return ResponseEntity.badRequest().body(response);
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
        return new ResponseEntity<>(apiErrorResponse, httpStatus);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    private static class ApiErrorResponse{
        private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss[.SSSSSSSSS]['Z']";

        @JsonProperty
        private Integer status;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
        private LocalDateTime timestamp;
        @JsonProperty
        private String resource;
        @JsonProperty("Detalle")
        private String detail;
        @JsonProperty("Error")
        private Map<String, String> errors;
    }
}
