package com.menu.app.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.menu.app.domain.controllers.LoginController;
import com.menu.app.domain.controllers.RegisterController;
import com.menu.app.infrastructure.receivers.web.authentication.AuthController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public LoginController getLoginController() {
        return new LoginController();
    }

    @Bean
    public RegisterController getRegisterController() {
        return new RegisterController();
    }

}
