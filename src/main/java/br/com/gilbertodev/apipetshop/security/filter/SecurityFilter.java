package br.com.gilbertodev.apipetshop.security.filter;

import br.com.gilbertodev.apipetshop.enums.messages.UsuarioMessages;
import br.com.gilbertodev.apipetshop.exceptions.AuthenticationErrorException;
import br.com.gilbertodev.apipetshop.repositories.UsuarioRepository;
import br.com.gilbertodev.apipetshop.security.token.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository repository;
    private final HandlerExceptionResolver resolver;

    public SecurityFilter(TokenService tokenService, UsuarioRepository repository, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.tokenService = tokenService;
        this.repository = repository;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            var tokenJWT = recuperarToken(request);

            if (tokenJWT != null) {
                autenticarUsuario(tokenJWT);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            resolver.resolveException(request, response, null, e);
        }
    }

    private void autenticarUsuario(String tokenJWT) {
        var subject = tokenService.getSubject(tokenJWT);
        var usuario = repository.findByLogin(subject);

        if (usuario == null) {
            throw new AuthenticationErrorException(UsuarioMessages.ERRO_AUTENTICACAO);
        }

        var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        return (authorizationHeader != null) ? authorizationHeader.replace("Bearer ", "") : null;
    }
}