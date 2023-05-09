package com.BlogApplication.UserService.query.event;

import com.BlogApplication.UserService.core.entity.ERole;
import com.BlogApplication.UserService.core.entity.Role;
import com.BlogApplication.UserService.core.entity.User;
import com.BlogApplication.UserService.core.events.UserCreatedEvent;
import com.BlogApplication.UserService.core.events.UserDeletedEvent;
import com.BlogApplication.UserService.query.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
@KafkaListener(topics = "UserEventsTopic",groupId = "user-query")

public class KafkaEventhandler {
    @Autowired
    private EventRepository repo;

@KafkaHandler
    public void listen(@Payload UserCreatedEvent userCreatedEvent){
        log.info("inside event handlder");
        repo.save(userCreatedEvent);
    }
    @KafkaHandler
    public void listen(@Payload UserDeletedEvent userDeletedEvent){
        log.info("inside event hanldder");
        repo.save(userDeletedEvent);
    }
}
