package com.BlogApplication.blogService.core.events;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.UUID;

@Document
@Data
public class BlogEvent {
    @Id
    private UUID id;
}
