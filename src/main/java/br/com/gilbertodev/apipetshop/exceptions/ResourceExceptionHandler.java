package br.com.gilbertodev.apipetshop.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<StandardError> business(BusinessException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        StandardError err = montarErro(e.getCodigo(), e.getMessage(), status, request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = montarErro(e.getCodigo(), e.getMessage(), status, request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<StandardError> auth(AuthException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError err = montarErro(e.getCodigo(), e.getMessage(), status, request);
        return ResponseEntity.status(status).body(err);
    }

    private StandardError montarErro(String codigo, String mensagem, HttpStatus status, HttpServletRequest request) {
        return new StandardError(
                Instant.now(),
                status.value(),
                codigo,
                "Erro na Requisição",
                mensagem,
                request.getRequestURI()
        );
    }
}
