package com.gulsun.user_ms.dtos.user;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

@Getter
@Setter
public class UpdateUser {
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;
    @Email(message = "Email is invalid")
    private String email;
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}
