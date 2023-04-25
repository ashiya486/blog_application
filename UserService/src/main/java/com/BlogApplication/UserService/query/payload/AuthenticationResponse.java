package com.BlogApplication.UserService.query.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private  String jwt;
}
