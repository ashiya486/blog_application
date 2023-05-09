package com.BlogApplication.UserService.core.events;

import lombok.Data;

import java.util.UUID;
@Data
public class UserDeletedEvent extends UserEvent{

    private String username;
    public UUID getId() {
        return super.getId();
    }

    public void setId(UUID id) {
        super.setId(id);
    }

}
