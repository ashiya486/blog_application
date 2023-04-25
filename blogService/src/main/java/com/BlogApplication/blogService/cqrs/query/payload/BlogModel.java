package com.BlogApplication.blogService.cqrs.query.payload;

import com.BlogApplication.blogService.core.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogModel {
    private UUID id;
    private String author;
    private String topic;
    private String content;
    private LocalDateTime timestamp;
    private Category category;

}
