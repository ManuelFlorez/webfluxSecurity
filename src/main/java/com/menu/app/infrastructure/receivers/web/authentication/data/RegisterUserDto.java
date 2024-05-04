package com.menu.app.infrastructure.receivers.web.authentication.data;

import com.menu.app.domain.entities.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegisterUserDto {

    @NotEmpty(message = "The userName is required.")
    private String userName;

    @NotEmpty(message = "The password is required.")
    private String password;

    public User toUser() {
        return User.builder()
                .userName(userName)
                .password(password)
                .build();
    }

    public static RegisterUserDto toMe(User user) {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setUserName(user.getUserName());
        registerUserDto.setPassword(user.getPassword());
        return registerUserDto;
    }

}