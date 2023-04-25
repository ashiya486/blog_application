package com.BlogApplication.blogService.cqrs.query.projection;

import com.BlogApplication.blogService.cqrs.command.repository.Blogrepository;
import com.BlogApplication.blogService.cqrs.command.repository.CategoryRepository;
import com.BlogApplication.blogService.core.entity.Blog;
import com.BlogApplication.blogService.core.entity.Category;
import com.BlogApplication.blogService.cqrs.query.payload.BlogModel;
import com.BlogApplication.blogService.cqrs.query.query.GetAllBlogsQuery;
import com.BlogApplication.blogService.cqrs.query.query.GetBlogsBetweenDates;
import com.BlogApplication.blogService.cqrs.query.query.GetBlogsByCategory;
import com.BlogApplication.blogService.cqrs.query.query.GetBlogsByUsername;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class BlogProjection {
    @Autowired
    private Blogrepository blogRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @QueryHandler
    public List<BlogModel> handle(GetAllBlogsQuery getAllBlogsQuery){
        List<Blog> list_blog_ent=this.blogRepository.findAll();
        List<BlogModel> list_blog_model=list_blog_ent.stream().map(blog -> new BlogModel(blog.getId(),blog.getAuthor(),blog.getTopic(),blog.getContent(),blog.getTimestamp(),blog.getCategory())).collect(Collectors.toList());
        return list_blog_model;

    }
    @QueryHandler List<BlogModel>handle(GetBlogsByCategory getBlogsByCategory){
        log.info("inside query handler");
        Category category= categoryRepository.findCategoryByName(getBlogsByCategory.getName());
        List<Blog>blogs_ent=blogRepository.findByCategoryId(category.getId());
        List<BlogModel>blogs_mod=new ArrayList<>();
        blogs_ent.stream().forEach(blog ->blogs_mod.add(new BlogModel(blog.getId(),blog.getAuthor(),blog.getTopic(),blog.getContent(),blog.getTimestamp(),blog.getCategory())) );
        return blogs_mod;
    }
    @QueryHandler List<BlogModel> handle(GetBlogsByUsername getBlogsByUsername){
        log.info("inside query handler");
        List<Blog> blogs_ent= this.blogRepository.findAllByAuthor(getBlogsByUsername.getName());
        List<BlogModel>blogs_mod=new ArrayList<>();
        blogs_ent.forEach(blog ->blogs_mod.add(new BlogModel(blog.getId(),blog.getAuthor(),blog.getTopic(),blog.getContent(),blog.getTimestamp(),blog.getCategory())) );
        return blogs_mod;
    }
    @QueryHandler List<BlogModel> handle(GetBlogsBetweenDates getBlogsBetweenDates){
        log.info("inside query handler");
        LocalDate startLocalDate = LocalDate.parse(getBlogsBetweenDates.getStartDate());
        LocalDate endLocalDate=LocalDate.parse(getBlogsBetweenDates.getEndDate());
        LocalDateTime start=startLocalDate.atStartOfDay();
        LocalDateTime end=endLocalDate.atStartOfDay();
        List<Blog> blogs_ent=this.blogRepository.findAllByTimestampBetween(start,end);
        List<BlogModel>blogs_mod=new ArrayList<>();
        blogs_ent.forEach(blog ->blogs_mod.add(new BlogModel(blog.getId(),blog.getAuthor(),blog.getTopic(),blog.getContent(),blog.getTimestamp(),blog.getCategory())) );
    return blogs_mod;

    }
}
