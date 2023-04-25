package com.BlogApplication.blogService.cqrs.command.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor@NoArgsConstructor
public class DeleteBlogCommand {
    private String Topic;

}
