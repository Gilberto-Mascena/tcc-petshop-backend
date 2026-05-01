package br.com.gilbertodev.apipetshop.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ResourceExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<StandardError> handleBusinessException(BusinessException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        StandardError err = montarErro(e.getCodigo(), e.getMessage(), status, request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> handleObjectNotFoundException(ObjectNotFoundException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = montarErro(e.getCodigo(), e.getMessage(), status, request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<StandardError> handleAuthException(AuthException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError err = montarErro(e.getCodigo(), e.getMessage(), status, request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(TypeMismatchException.class)

    public ResponseEntity<StandardError> handleTypeMismatchException(TypeMismatchException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = montarErro(e.getCodigo(), e.getMessage(), status, request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StandardError> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e, HttpServletRequest request) {

        String mensagem = "O parâmetro '" + e.getName() + "' deve ser do tipo " + e.getRequiredType().getSimpleName();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = montarErro("PARAM_INVALIDO", mensagem, status, request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleGenericException(Exception e, HttpServletRequest request) {

        log.error("Erro inesperado: ", e);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StandardError err = montarErro("ERRO_INTERNO", "Ocorreu um erro interno inesperado. Tente novamente mais tarde.", status, request);
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
