package com.menu.app.domain.controllers;

import com.menu.app.domain.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterControllerTest {

    private User user;

    private RegisterController registerController;

    @BeforeEach
    void init() {
        user = User.builder()
                .userName("M")
                .password("p")
                .build();

        user.setUserName("Manuel");
        user.setPassword("pass123");

        registerController = new RegisterController();
    }

    @Test
    void register() {
        StepVerifier.create(registerController.register(user))
                .expectNext()
                .assertNext(u -> {
                    assertEquals("Manuel", u.getUserName());
                    assertEquals("pass123", u.getPassword());
                })
                .verifyComplete();
    }
}
