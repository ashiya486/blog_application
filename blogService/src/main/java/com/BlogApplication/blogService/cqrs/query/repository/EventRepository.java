package com.BlogApplication.blogService.cqrs.query.repository;

import com.BlogApplication.blogService.core.events.BlogEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface EventRepository extends MongoRepository<BlogEvent, UUID> {
}
