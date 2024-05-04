package com.menu.app.infrastructure.receivers.web.authentication;

import com.menu.app.domain.controllers.LoginController;
import com.menu.app.domain.controllers.RegisterController;
import com.menu.app.infrastructure.receivers.web.authentication.data.RegisterUserDto;
import com.menu.app.infrastructure.receivers.web.commons.Response;
import com.menu.app.infrastructure.receivers.web.commons.ResponseApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AuthController implements Response<RegisterUserDto> {

    private final RegisterController registerController;
    private final LoginController loginController;

    @Autowired
    public AuthController(LoginController loginController, RegisterController registerController) {
        this.loginController = loginController;
        this.registerController = registerController;
    }

    @PostMapping(value = "/login")
    public Mono<ResponseEntity<ResponseApi<RegisterUserDto>>> login(@Valid @RequestBody RegisterUserDto user) {
        return loginController.login(user.toUser())
                .map(RegisterUserDto::toMe)
                .flatMap(this::response);
    }

    @PostMapping(value = "/register")
    public Mono<ResponseEntity<ResponseApi<RegisterUserDto>>> register(@Valid @RequestBody RegisterUserDto user) {
        return registerController.register(user.toUser())
                .map(RegisterUserDto::toMe)
                .flatMap(this::response);
    }

}