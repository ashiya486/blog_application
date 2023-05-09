package com.BlogApplication.blogService.cqrs.command.repository;

import com.BlogApplication.blogService.core.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface Blogrepository extends JpaRepository<Blog, UUID> {
    List<Blog> findByCategoryId(Long id);
    List<Blog> findAllByAuthor(String name);
    List<Blog> findAllByTimestampBetween(LocalDateTime start ,LocalDateTime end);
    boolean existsBlogByTopic(String topic);
    void deleteByTopic(String topic);
}
