package com.BlogApplication.UserService.query.repository;

import com.BlogApplication.UserService.core.events.UserEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface EventRepository extends MongoRepository<UserEvent, UUID> {
}
