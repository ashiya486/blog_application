package com.BlogApplication.UserService.core.model;

import com.BlogApplication.UserService.core.entity.ERole;
import lombok.Data;

@Data
public class RoleModel {
    private long id;


    private ERole name;
}
