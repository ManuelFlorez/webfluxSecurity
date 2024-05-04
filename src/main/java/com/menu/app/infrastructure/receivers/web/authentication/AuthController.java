package com.menu.app.infrastructure.receivers.web.authentication;

import com.menu.app.domain.controllers.LoginController;
import com.menu.app.domain.controllers.RegisterController;
import com.menu.app.infrastructure.receivers.web.authentication.data.RegisterUserDto;
import com.menu.app.infrastructure.receivers.web.commons.Response;
import com.menu.app.infrastructure.receivers.web.commons.ResponseApi;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AuthController implements Response<RegisterUserDto> {

    private final LoginController loginController;

    private final RegisterController registerController;

    public AuthController(LoginController loginController, RegisterController registerController) {
        this.registerController = registerController;
        this.loginController = loginController;
    }

    @PostMapping(value = "/login")
    public Mono<ResponseEntity<ResponseApi<RegisterUserDto>>> login(@Valid @RequestBody RegisterUserDto user) {
        return loginController.login(user.toUser())
                .flatMap(u -> response(RegisterUserDto.builder()
                        .userName(u.getUserName())
                        .password(u.getPassword())
                        .build()) );
    }

    @PostMapping(value = "/register")
    public Mono<ResponseEntity<ResponseApi<RegisterUserDto>>> register(@Valid @RequestBody RegisterUserDto user) {
        return registerController.register(user.toUser())
                .flatMap(u -> response(RegisterUserDto.builder()
                        .userName(u.getUserName())
                        .password(u.getPassword())
                        .build()) );
    }

}