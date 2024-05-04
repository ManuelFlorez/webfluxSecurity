package com.menu.app.domain.controllers;

import com.menu.app.domain.entities.User;
import reactor.core.publisher.Mono;

public class RegisterController {
    public Mono<User> register(User user) {
        return Mono.just(user);
    }
}
