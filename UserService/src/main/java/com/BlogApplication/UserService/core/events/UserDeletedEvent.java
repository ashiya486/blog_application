package com.BlogApplication.UserService.core.events;

import lombok.Data;

import java.util.UUID;
@Data
public class UserDeletedEvent {

    private UUID id;
    private String username;

}
