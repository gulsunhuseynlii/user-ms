package com.gulsun.user_ms.dtos.user;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUser {
    private String username;
    private String email;
    @AssertTrue(message = "User or Email can not be null")
    private boolean isUsernameOrEmailPresent(){
        return (username != null && !username.isBlank())
                || (email != null && !email.isBlank());
    }
    private Long roleId;
}
