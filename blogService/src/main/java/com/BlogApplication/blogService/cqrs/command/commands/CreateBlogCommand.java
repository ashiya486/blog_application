package com.BlogApplication.blogService.cqrs.command.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


import java.time.LocalDateTime;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBlogCommand {
    @TargetAggregateIdentifier
    private UUID id;
    private String author;
    private String topic;
    private String content;
    private LocalDateTime timestamp;
    private String category;

}
