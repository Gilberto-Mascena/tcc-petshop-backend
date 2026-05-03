package br.com.gilbertodev.apipetshop.security.handler;

import br.com.gilbertodev.apipetshop.enums.messages.UsuarioMessages;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final HandlerExceptionResolver resolver;

    public CustomAccessDeniedHandler(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) {

        var minhaExcecao = new br.com.gilbertodev.apipetshop.exceptions.AccessDeniedException(UsuarioMessages.ERRO_ACESSO_NEGADO);
        resolver.resolveException(request, response, null, minhaExcecao);
    }
}