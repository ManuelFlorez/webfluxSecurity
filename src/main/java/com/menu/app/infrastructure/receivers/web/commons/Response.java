package com.menu.app.infrastructure.receivers.web.commons;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

public interface Response<T> {
    String MESSAGE_SUCCESS = "success";
    String MESSAGE_FAILED = "failed";
    default Mono<ResponseEntity<ResponseApi<T>>> ok(T data) {
        return Mono.just(new ResponseApi<>(data, MESSAGE_SUCCESS))
                .map(responseApi -> new ResponseEntity<>(
                        responseApi,
                        HttpStatus.OK));
    }
    default Consumer<Throwable> error() {
        return err -> Mono.just(new ResponseApi<>(err.getMessage(), MESSAGE_FAILED))
                .map(responseApi -> new ResponseEntity<>(
                        responseApi,
                        HttpStatus.INTERNAL_SERVER_ERROR));
    }
    default Mono<ResponseEntity<ResponseApi<T>>> response(T t) {
        return this.ok(t).doOnError(this.error());
    }
}