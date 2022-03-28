package com.estacoes.estacoes.exceptions;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class TodasExceptionHandler extends ResponseEntityExceptionHandler {

    private final String erroDescricao = "Erro na sua solicitação. Observe erro(s) a seguir:";

    @ExceptionHandler(value = {IllegalArgumentException.class} )
    public ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {

        ApiError erro = new ApiError(erroDescricao, ex.getMessage(), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<ApiError>(erro, new HttpHeaders(), erro.getStatus());
    }
}