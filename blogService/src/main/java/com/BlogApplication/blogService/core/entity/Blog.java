package com.BlogApplication.blogService.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Blog {
    @Id
    @Type(type = "uuid-char")
    private UUID id;
    @Column(nullable = false)
    private String author;
    @Column(unique = true,nullable = false)
    private String topic;
    @Column(unique = true,nullable = false)
    private String content;
    private LocalDateTime timestamp;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="category_id")
//    @JsonIgnore
    private Category category;

}
