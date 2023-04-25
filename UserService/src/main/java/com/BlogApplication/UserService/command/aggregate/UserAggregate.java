package com.BlogApplication.UserService.command.aggregate;

import com.BlogApplication.UserService.command.commands.CreateUserCommand;
import com.BlogApplication.UserService.command.commands.DeleteUserCommand;
import com.BlogApplication.UserService.core.events.UserCreatedEvent;
import com.BlogApplication.UserService.core.events.UserDeletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.Set;
import java.util.UUID;

@Slf4j
@Aggregate
public class UserAggregate {
    @AggregateIdentifier
    private UUID Id;
    private String username;
    private String email;
    private String password;
    private Set<String> roles;
    @CommandHandler
    public UserAggregate(CreateUserCommand createUserCommand){
        log.info("inside command handler:"+createUserCommand.toString());
        UserCreatedEvent userCreatedEvent= UserCreatedEvent.builder()
                .username(createUserCommand.getUsername())
                .email(createUserCommand.getEmail())
                .password(createUserCommand.getPassword())
                .roles(createUserCommand.getRoles()).build();
        userCreatedEvent.setId(createUserCommand.getUserId());
        log.info("command handler"+createUserCommand.getUserId().toString()+userCreatedEvent.getId().toString());
        AggregateLifecycle.apply(userCreatedEvent);

    }
    @CommandHandler
    public UserAggregate(DeleteUserCommand deleteUserCommand){
        UserDeletedEvent userDeletedEvent=new UserDeletedEvent();
        BeanUtils.copyProperties(deleteUserCommand,userDeletedEvent);
        log.info("command handler-delete");
        AggregateLifecycle.apply(userDeletedEvent);
    }
    @EventSourcingHandler
    public void on(UserDeletedEvent userDeletedEvent){
        this.Id =userDeletedEvent.getId();
        this.username=userDeletedEvent.getUsername();
    }
    public UserAggregate(){

    }
    @EventSourcingHandler
    public void on(UserCreatedEvent userCreatedEvent){
        this.Id =userCreatedEvent.getId();
        this.username=userCreatedEvent.getUsername();
        this.email=userCreatedEvent.getEmail();
        this.password=userCreatedEvent.getPassword();
        this.roles=userCreatedEvent.getRoles();
        log.info("evenrt sourcing");
    }

}

