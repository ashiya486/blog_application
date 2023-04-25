package com.BlogApplication.UserService.command.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteUserCommand {
    @TargetAggregateIdentifier
    private UUID id;

    private String username;


}
