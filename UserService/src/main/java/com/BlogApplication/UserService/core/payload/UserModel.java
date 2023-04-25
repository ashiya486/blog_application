package com.BlogApplication.UserService.core.payload;

import com.BlogApplication.UserService.core.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
@Data@AllArgsConstructor@NoArgsConstructor
public class UserModel {
    private UUID userId;
    private String username;
    private String email;
    private String password;
    private Set<Role> roles=new HashSet<>();


}
