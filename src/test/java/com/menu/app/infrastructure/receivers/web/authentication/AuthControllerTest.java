package com.menu.app.infrastructure.receivers.web.authentication;

import com.menu.app.infrastructure.receivers.web.authentication.data.RegisterUserDto;

import com.menu.app.domain.controllers.LoginController;
import com.menu.app.domain.controllers.RegisterController;
import com.menu.app.domain.entities.User;

import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = AuthController.class)
class AuthControllerTest {

    @MockBean
    private LoginController loginController;

    @MockBean
    private RegisterController registerController;

    @Autowired
    private WebTestClient webClient;

    private RegisterUserDto registerUserDto;

    final String URI_LOGIN = "/login";
    final String URI_REGISTER = "/register";

    @BeforeEach
    void init() {
        registerUserDto = RegisterUserDto.builder()
                .userName("Manuel")
                .password("pass123")
                .build();
    }

    @Test
    void validationHandleError() {
        given( loginController.login(any(User.class)) )
                .willThrow(new NumberFormatException("kkk"));

        WebTestClient.ResponseSpec response = webClient.post()
                .uri(URI_LOGIN)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(registerUserDto), RegisterUserDto.class)
                .exchange();

        response.expectStatus()
                .is5xxServerError();
    }

    @Test
    void consumerResponseError() {
        when( loginController.login(any(User.class)) )
                .thenReturn(Mono.error(new RuntimeException("Exception")));

        WebTestClient.ResponseSpec response = webClient.post().uri(URI_LOGIN)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(registerUserDto), RegisterUserDto.class)
                .exchange();

        response.expectStatus()
                .is5xxServerError()
                .expectBody()
                .jsonPath("$.error").isEqualTo("Internal Server Error")
                .jsonPath("$.path").isEqualTo("/login");
    }

    @Test
    void testLoginSuccess() {
        given( loginController.login(any(User.class)) )
                .willReturn(Mono.just(registerUserDto.toUser()));

        WebTestClient.ResponseSpec response = webClient.post()
                .uri(URI_LOGIN)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(registerUserDto), RegisterUserDto.class)
                .exchange();

        response.expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.message").isEqualTo("success");
    }

    @Test
    void testLoginUserNameIsEmpty() {
        this.registerUserDto.setUserName(null);

        webClient.post()
                .uri(URI_LOGIN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(registerUserDto))
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody()
                .jsonPath("$.userName").isEqualTo("The userName is required.");
    }

    @Test
    void testLoginPasswordIsEmpty() {
        this.registerUserDto.setPassword(null);

        webClient.post()
                .uri(URI_LOGIN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(registerUserDto))
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody()
                .jsonPath("$.password").isEqualTo("The password is required.");
    }

    @Test
    void testRegister() {
        Mono<User> userReturn = Mono.just(registerUserDto.toUser());

        given(registerController.register(any(User.class)))
                .willReturn(userReturn);

        webClient.post()
                .uri(URI_REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(registerUserDto))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.message").isEqualTo("success");
    }

}