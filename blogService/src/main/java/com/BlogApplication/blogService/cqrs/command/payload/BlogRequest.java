package com.BlogApplication.blogService.cqrs.command.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogRequest {
    private UUID id;
    @NotEmpty(message="authprName can't be empty")
    @Min(1)
    private String author;
    @NotEmpty(message="authprName can't be empty")
    @Min((1))
    private String topic;
    @NotEmpty(message="authprName can't be empty")
    @Min((1))
    private String content;
    private LocalDateTime timestamp;
    @NotEmpty(message="authprName can't be empty")
    @Min(1)
    private String category;



}
