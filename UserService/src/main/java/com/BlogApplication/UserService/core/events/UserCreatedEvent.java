package com.BlogApplication.UserService.core.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreatedEvent extends UserEvent {
    private String username;
    private String email;
    private Set<String> roles;
    private String password;
    public UUID getId() {
        return super.getId();
    }

    public void setId(UUID id) {
        super.setId(id);
    }
}
