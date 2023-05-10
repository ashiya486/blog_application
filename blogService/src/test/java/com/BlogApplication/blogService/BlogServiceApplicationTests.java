package com.BlogApplication.blogService;

import com.BlogApplication.blogService.core.entity.Blog;
import com.BlogApplication.blogService.core.entity.Category;
import com.BlogApplication.blogService.cqrs.command.repository.Blogrepository;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@SpringBootTest
class BlogServiceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Mock
	private Blogrepository blogrepository;
	@BeforeEach
	public void setup(){
		MockitoAnnotations.openMocks(this);
	}
	@Test
	public void getBlogByCategory(){
		Long category= 1L;
		List<Blog> blogs=new ArrayList<>();
		blogs.add(new Blog(UUID.randomUUID(),"harry","sample topic","sample content", LocalDateTime.now(),new Category(1,"science")));
		blogs.add(new Blog(UUID.randomUUID(),"rachel","sample topic2","sample content2", LocalDateTime.now(),new Category(1,"science")));
		when(blogrepository.findByCategoryId(category)).thenReturn(blogs);
		List<Blog> result=blogrepository.findByCategoryId(category);
		Assertions.assertEquals(blogs,result);
	}

}