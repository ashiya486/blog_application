package com.BlogApplication.blogService.cqrs.command.aggregate;

import com.BlogApplication.blogService.core.events.BlogDeletedEvent;
import com.BlogApplication.blogService.cqrs.command.commands.CreateBlogCommand;
import com.BlogApplication.blogService.core.events.BlogCreatedEvent;
import com.BlogApplication.blogService.cqrs.command.commands.DeleteBlogCommand;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.UUID;

@Aggregate
@Slf4j
public class BlogAggregate {
    @AggregateIdentifier
    private UUID id;
    private String author;
    private String topic;
    private String content;
    private LocalDateTime timestamp;
    private String category;
    @CommandHandler
    public BlogAggregate(CreateBlogCommand createBlogCommand){
        log.info("command handler:create blog "+createBlogCommand.toString());
        BlogCreatedEvent blogCreatedEvent=new BlogCreatedEvent();
        BeanUtils.copyProperties(createBlogCommand,blogCreatedEvent);
        AggregateLifecycle.apply(blogCreatedEvent);
    }
    @CommandHandler
    public BlogAggregate(DeleteBlogCommand deleteBlogCommand){
        log.info("command handler:create blog ");
        BlogDeletedEvent blogDeletedEvent=new BlogDeletedEvent();
        BeanUtils.copyProperties(deleteBlogCommand,blogDeletedEvent);
        AggregateLifecycle.apply(blogDeletedEvent);

    }
    @EventSourcingHandler
    public void on(BlogDeletedEvent blogDeletedEvent){
        this.id=blogDeletedEvent.getId();
        this.topic=blogDeletedEvent.getTopic();
    }

    public BlogAggregate() {
    }
    @EventSourcingHandler
    public void on(BlogCreatedEvent blogCreatedEvent){
        log.info("event sourcing:create blog"+blogCreatedEvent.toString());
        this.id=blogCreatedEvent.getId();
        this.author=blogCreatedEvent.getAuthor();
        this.topic=blogCreatedEvent.getTopic();
        this.content=blogCreatedEvent.getContent();
        this.timestamp=blogCreatedEvent.getTimestamp();
        this.category=blogCreatedEvent.getCategory();
    }




}
