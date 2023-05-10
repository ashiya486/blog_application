package com.BlogApplication.UserService.query.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotEmpty(message="Username can't be empty")
    private String username;
    @NotEmpty(message="password can't be empty")
    private String password;
}
