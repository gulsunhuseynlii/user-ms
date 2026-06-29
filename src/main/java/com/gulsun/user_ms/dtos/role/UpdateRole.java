package com.gulsun.user_ms.dtos.role;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRole {
    @NotBlank(message = "Name can not be blank")
    private String name;
}
