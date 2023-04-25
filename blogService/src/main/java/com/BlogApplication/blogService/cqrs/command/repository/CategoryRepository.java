package com.BlogApplication.blogService.cqrs.command.repository;

import com.BlogApplication.blogService.core.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findCategoryByName(String name);
}
