package com.BlogApplication.blogService.cqrs.query.events;


import com.BlogApplication.blogService.core.events.BlogCreatedEvent;
import com.BlogApplication.blogService.core.events.BlogDeletedEvent;
import com.BlogApplication.blogService.cqrs.query.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@KafkaListener(topics = "BlogEventsTopic",groupId = "user-query")
public class KafkaEventsHandler {
    @Autowired
    private EventRepository repo;
    @KafkaHandler
public void listen(@Payload BlogCreatedEvent blogCreatedEvent){
    log.info("kafka handler"+blogCreatedEvent.getId().toString());
//    repo.save(blogCreatedEvent);
}
        @KafkaHandler
    public void listen(@Payload BlogDeletedEvent blogDeletedEvent){
        log.info("kafka handler"+blogDeletedEvent.getId().toString());
//    repo.save(blogCreatedEvent);
    }

}
