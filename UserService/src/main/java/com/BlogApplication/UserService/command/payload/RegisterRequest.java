package com.BlogApplication.UserService.command.payload;

import lombok.Data;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;
@Data
public class RegisterRequest {

    private String userId;

    @NotEmpty(message="Username can't be empty")
    private String username;
   @NotEmpty(message="email can't be empty")
    @Email(message="email address not valid")
    @Pattern(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.(com)$",message = "email must  contain @ and end in .com")
    private String email;
    @NotEmpty(message="email can't be empty")
    @Pattern(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+$",message = "password should be alphanumeric")
    @Min(8)
    private String password;

    private Set<String> roles=new HashSet<>();



}
