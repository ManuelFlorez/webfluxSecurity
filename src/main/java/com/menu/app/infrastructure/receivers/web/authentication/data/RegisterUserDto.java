package com.menu.app.infrastructure.receivers.web.authentication.data;

import com.menu.app.domain.entities.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
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
        return RegisterUserDto.builder()
                .userName(user.getUserName())
                .password(user.getPassword())
                .build();
    }

}