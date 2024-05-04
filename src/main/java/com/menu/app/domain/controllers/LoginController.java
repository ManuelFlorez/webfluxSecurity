package com.menu.app.domain.controllers;

import com.menu.app.domain.entities.User;
import reactor.core.publisher.Mono;

public class LoginController {
    public Mono<User> login(User user) {
        return Mono.just(user);
    }
}
