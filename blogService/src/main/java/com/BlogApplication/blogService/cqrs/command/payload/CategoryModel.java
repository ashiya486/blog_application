package com.BlogApplication.blogService.cqrs.command.payload;

import lombok.Data;

@Data
public class CategoryModel {
    private long id;
    private String name;
}
