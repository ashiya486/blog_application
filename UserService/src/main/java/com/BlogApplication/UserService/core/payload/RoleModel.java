package com.BlogApplication.UserService.core.payload;

import com.BlogApplication.UserService.core.entity.ERole;
import lombok.Data;

@Data
public class RoleModel {
    private long id;
    private ERole name;
}
