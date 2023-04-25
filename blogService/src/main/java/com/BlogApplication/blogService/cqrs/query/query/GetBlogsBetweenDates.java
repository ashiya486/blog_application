package com.BlogApplication.blogService.cqrs.query.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor
@NoArgsConstructor
public class GetBlogsBetweenDates {
    private String startDate;
    private String endDate;
}
