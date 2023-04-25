package com.BlogApplication.UserService.command.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserCommand {
    @TargetAggregateIdentifier
    private UUID userId;
    private String username;
    private String email;
    private String password;
    private Set<String> roles=new HashSet<>();

}
