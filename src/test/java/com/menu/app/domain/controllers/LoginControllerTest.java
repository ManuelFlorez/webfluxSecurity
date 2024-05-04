package com.menu.app.domain.controllers;

import com.menu.app.domain.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginControllerTest {

    private User user;

    private LoginController loginController;

    @BeforeEach
    void init() {
        user = User.builder()
                .userName("Manuel")
                .password("pass123")
                .build();
        loginController = new LoginController();
    }

    @Test
    void login() {
        StepVerifier.create(loginController.login(user))
                .expectNext()
                .assertNext(u -> {
                    assertEquals("Manuel", u.getUserName());
                    assertEquals("pass123", u.getPassword());
                })
                .verifyComplete();
    }

}
