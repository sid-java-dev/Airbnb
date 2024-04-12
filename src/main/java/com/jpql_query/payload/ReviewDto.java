package com.jpql_query.payload;

import com.jpql_query.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private List<Review> reviewList;
    private double overallRating;
}
