package br.com.gilbertodev.apipetshop.exceptions;

import br.com.gilbertodev.apipetshop.interfaces.MessageBase;
import br.com.gilbertodev.apipetshop.messages.GlobalMessages;
import br.com.gilbertodev.apipetshop.messages.UsuarioMessages;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ResourceExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ResourceExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<StandardError> handleBusinessException(BusinessException ex, HttpServletRequest request) {

        log.warn("Regra de negócio violada na rota [{}]: Código: {}, Mensagem: {}",
                request.getRequestURI(),
                ex.getCodigo(),
                ex.getMessage()
        );

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        StandardError err = montarErro(
                ex.getCodigo(),
                ex.getMessage(),
                status,
                request
        );

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> handleObjectNotFoundException(ObjectNotFoundException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = montarErro(
                ex.getCodigo(),
                ex.getMessage(),
                status,
                request
        );

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<StandardError> handleAuthException(AuthException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError err = montarErro(
                ex.getCodigo(),
                ex.getMessage(),
                status,
                request
        );

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler({AccessDeniedException.class, org.springframework.security.access.AccessDeniedException.class})
    public ResponseEntity<StandardError> handleAccessDeniedException(Exception ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.FORBIDDEN;
        StandardError err = montarErro(
                GlobalMessages.ACESSO_NEGADO.getCodigo(),
                GlobalMessages.ACESSO_NEGADO.getMensagem(),
                status,
                request
        );

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<StandardError> handleAuthorizationDeniedException(AuthorizationDeniedException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.FORBIDDEN;
        StandardError err = montarErro(
                GlobalMessages.ACESSO_NEGADO.getCodigo(),
                GlobalMessages.ACESSO_NEGADO.getMensagem(),
                status,
                request
        );

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(AuthenticationErrorException.class)
    public ResponseEntity<StandardError> handleAuthenticationException(AuthenticationErrorException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError err = montarErro(
                ex.getCodigo(),
                ex.getMessage(),
                status,
                request
        );

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<StandardError> handleUsernameNotFound(UsernameNotFoundException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError err = montarErro(
                UsuarioMessages.CREDENCIAIS_INVALIDAS.getCodigo(),
                UsuarioMessages.CREDENCIAIS_INVALIDAS.getMensagem(),
                status,
                request
        );

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = montarErro(
                GlobalMessages.DADOS_INVALIDOS.getCodigo(),
                GlobalMessages.DADOS_INVALIDOS.getMensagem(),
                status,
                request
        );

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {

        log.warn("Falha de validação de campos na rota [{}]. Quantidade de erros: {}",
                request.getRequestURI(),
                ex.getBindingResult()
                        .getFieldErrorCount()
        );

        List<ValidationErrorDetails> erros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> error.getDefaultMessage() != null ? error.getDefaultMessage() : "Campo inválido",
                        (existingMessage, newMessage) -> existingMessage
                ))
                .entrySet()
                .stream()
                .map(entry -> new ValidationErrorDetails(entry.getKey(), entry.getValue()))
                .toList();

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ValidationErrorResponse err = montarErroValidacao(
                status,
                request,
                erros
        );

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<StandardError> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        StandardError err = montarErro(
                GlobalMessages.METODO_INVALIDO,
                status,
                request
        );

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<StandardError> handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {

        log.warn("Tentativa de login inválida originada do IP: {}", request.getRemoteAddr());

        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError err = montarErro(
                UsuarioMessages.CREDENCIAIS_INVALIDAS.getCodigo(),
                UsuarioMessages.CREDENCIAIS_INVALIDAS.getMensagem(),
                status,
                request
        );

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> handleDataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        StandardError err = montarErro(
                GlobalMessages.CONFLITO_DADOS,
                status,
                request
        );

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StandardError> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {

        String tipoEsperado = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "definido.";
        String mensagemDinamica = "O parâmetro '" + ex.getName() + "' deve ser do tipo " + tipoEsperado;

        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = montarErro(
                GlobalMessages.PARAM_INVALIDO.getCodigo(),
                mensagemDinamica,
                status,
                request
        );

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<StandardError> handleTypeMismatch(TypeMismatchException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = montarErro(
                ex.getCodigo(),
                ex.getMessage(),
                status,
                request
        );

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleGenericException(Exception ex, HttpServletRequest request) {

        log.error("Erro inesperado: ", ex);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StandardError err = montarErro(
                GlobalMessages.ERRO_INTERNO.getCodigo(),
                GlobalMessages.ERRO_INTERNO.getMensagem(),
                status,
                request
        );

        return ResponseEntity.status(status).body(err);
    }

    private StandardError montarErro(MessageBase messageBase, HttpStatus status, HttpServletRequest request) {

        return new StandardError(
                Instant.now(),
                status.value(),
                messageBase.getCodigo(),
                "Erro na Requisição",
                messageBase.getMensagem(),
                request.getRequestURI()
        );
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

    private ValidationErrorResponse montarErroValidacao(HttpStatus status, HttpServletRequest request, List<ValidationErrorDetails> erros) {

        return new ValidationErrorResponse(
                Instant.now(),
                status.value(),
                GlobalMessages.VALIDACAO_FALHA.getCodigo(),
                "Erro na Requisição",
                GlobalMessages.VALIDACAO_FALHA.getMensagem(),
                request.getRequestURI(),
                erros
        );
    }
}