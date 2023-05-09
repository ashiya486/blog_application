package com.BlogApplication.blogService.cqrs.command.events;

import com.BlogApplication.blogService.core.events.BlogDeletedEvent;
import com.BlogApplication.blogService.cqrs.command.repository.Blogrepository;
import com.BlogApplication.blogService.cqrs.command.repository.CategoryRepository;
import com.BlogApplication.blogService.core.entity.Blog;
import com.BlogApplication.blogService.core.entity.Category;
import com.BlogApplication.blogService.core.events.BlogCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BlogEventHandler {
    @Autowired
    private Blogrepository blogrepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    @EventHandler
    public void on(BlogCreatedEvent blogCreatedEvent){
        log.info("inside event handler:create blog");

        Category cat=categoryRepository.findCategoryByName(blogCreatedEvent.getCategory());
        Blog blog= Blog.builder()
                .id(blogCreatedEvent.getId())
                .author(blogCreatedEvent.getAuthor())
                .topic(blogCreatedEvent.getTopic())
                .content(blogCreatedEvent.getContent())
                .category(cat).build();
        blogrepository.save(blog);
        log.info("publishing event to kafka");
       this.kafkaTemplate.send("BlogEventsTopic",blogCreatedEvent);
    }
    @EventHandler
    public void on(BlogDeletedEvent blogDeletedEvent){
        log.info("delete event handler"+blogDeletedEvent.toString());
        try{
            if(blogrepository.existsBlogByTopic(blogDeletedEvent.getTopic())){
                blogrepository.deleteByTopic(blogDeletedEvent.getTopic());
                this.kafkaTemplate.send("BlogEventsTopic",blogDeletedEvent);
            }
            log.info("blog deleted");
        }catch (Exception e){e.printStackTrace();}
    }

}
