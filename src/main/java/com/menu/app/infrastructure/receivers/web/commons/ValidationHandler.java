package com.menu.app.infrastructure.receivers.web.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@RequiredArgsConstructor
public class ValidationHandler implements WebExceptionHandler {

    private static final String EMPTY_VALUE = "";

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof WebExchangeBindException validationEx) {
            final Map<String, String> errors = getValidationErrors(validationEx);

            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

            return processMapper(exchange, errors);
        } else {
            return Mono.error(ex);
        }
    }

    public Mono<Void> processMapper(ServerWebExchange exchange, Map<String, String> errors) {
        try {
            return writeResponse(exchange, objectMapper.writeValueAsBytes(errors));
        } catch (JsonProcessingException e) {
            return Mono.error(e);
        }
    }

    private Map<String, String> getValidationErrors(final WebExchangeBindException validationEx) {

        return validationEx.getBindingResult()
                .getFieldErrors().stream()
                .collect(Collectors
                        .toMap(FieldError::getField,
                                error -> Optional.ofNullable(error.getDefaultMessage()).orElse(EMPTY_VALUE)));
    }

    private Mono<Void> writeResponse(final ServerWebExchange exchange, final byte[] responseBytes) {
        return exchange.getResponse().writeWith(
                Mono.just(exchange.getResponse()
                        .bufferFactory()
                        .wrap(responseBytes)));
    }

}
