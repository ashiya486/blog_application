package com.BlogApplication.blogService.core.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogCreatedEvent extends BlogEvent{

    public UUID getId() {
        return super.getId();
    }

    public void setId(UUID id) {
        super.setId(id);
    }

    private String author;
    private String topic;
    private String content;
    private LocalDateTime timestamp;
    private String category;



}
