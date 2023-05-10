package com.BlogApplication.blogService.cqrs.command.controller;

import com.BlogApplication.blogService.core.entity.Category;
import com.BlogApplication.blogService.core.exceptions.RestTemplateException;
import com.BlogApplication.blogService.cqrs.command.commands.CreateBlogCommand;
import com.BlogApplication.blogService.cqrs.command.commands.DeleteBlogCommand;
import com.BlogApplication.blogService.cqrs.command.payload.BlogRequest;
import com.BlogApplication.blogService.cqrs.command.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.UUID;
@RestController
@Slf4j
@RequestMapping("/blogs")
public class BlogController {
    private CommandGateway commandGateway;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CategoryRepository repo;


    public BlogController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }
    String validate_url="http://USER-SERVICE/user/validate";

    @PostMapping
    public ResponseEntity<?> createPost(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,@RequestBody BlogRequest blogRequest) {
        HttpHeaders header = new HttpHeaders();
        header.set("Authorization", jwt);
        HttpEntity<String> entity = new HttpEntity<>(header);
        ResponseEntity<Void> response = restTemplate.exchange(validate_url, HttpMethod.GET, entity, Void.class);
        if (response.getStatusCode() == HttpStatus.ACCEPTED) {
            CreateBlogCommand createBlogCommand = new CreateBlogCommand(UUID.randomUUID(), blogRequest.getAuthor(), blogRequest.getTopic(), blogRequest.getContent(), LocalDateTime.now(), blogRequest.getCategory());
           log.info("controller"+createBlogCommand.toString());
            return ResponseEntity.ok(this.commandGateway.sendAndWait(createBlogCommand));
//            return ResponseEnti/ty.ok(jwt);

        } else {
            throw new RestTemplateException(response.getStatusCode(), "access denied from auth server");

        }
    }
    @DeleteMapping("/delete/{blogName}")
    public ResponseEntity<?> deletePost(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,@PathVariable("blogName") String blogName) {
        HttpHeaders header = new HttpHeaders();
        header.set("AUTHORIZATION", jwt);
        HttpEntity<String> entity = new HttpEntity<>(header);
        ResponseEntity<Void> response = restTemplate.exchange(validate_url, HttpMethod.GET, entity, Void.class);
        if (response.getStatusCode() == HttpStatus.ACCEPTED) {
            DeleteBlogCommand deleteBlogCommand=new DeleteBlogCommand(UUID.randomUUID(),blogName);
            return ResponseEntity.ok(this.commandGateway.sendAndWait(deleteBlogCommand));
        } else {
            throw new RestTemplateException(response.getStatusCode(), "access denied from auth server");

        }
    }
    @PostMapping("/restore")
    public void restore(){
        Category cat1=new Category(1,"science");
        Category cat2=new Category(2,"drama");
        repo.save(cat1);
        repo.save(cat2);

    }


}
