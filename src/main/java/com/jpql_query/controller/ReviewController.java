package com.jpql_query.controller;

import com.jpql_query.entity.Property;
import com.jpql_query.entity.PropertyUser;
import com.jpql_query.entity.Review;
import com.jpql_query.payload.ReviewDto;
import com.jpql_query.repository.PropertyRepository;
import com.jpql_query.repository.PropertyUserRepository;
import com.jpql_query.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private PropertyUserRepository userRepo;
    private ReviewRepository reviewRepo;
    private PropertyRepository propertyRepository;

    public ReviewController(PropertyUserRepository userRepo, ReviewRepository reviewRepo, PropertyRepository propertyRepository) {
        this.userRepo = userRepo;
        this.reviewRepo = reviewRepo;
        this.propertyRepository = propertyRepository;
    }

    @PostMapping("/{propertyId}")
    public ResponseEntity<String> addReview(@PathVariable long propertyId,
                                            @RequestBody Review review,
                                            @AuthenticationPrincipal PropertyUser user
    ) {
        Review r = reviewRepo.findByReviewByPropertyIdAndPropertyUserId(propertyId, user.getId());
        if (r != null) {
            return new ResponseEntity<>("Review already exists", HttpStatus.CONFLICT);
        }
        Optional<Property> opProperty = propertyRepository.findById(propertyId);
        Property property = opProperty.get();
        review.setProperty(property);
        review.setPropertyUser(user);
        reviewRepo.save(review);
        return new ResponseEntity<>("Review added successfully", HttpStatus.OK);
    }

    @GetMapping
    public List<Review> getAllReviewsForCurrentUser(@AuthenticationPrincipal PropertyUser user) {
        List<Review> reviews = reviewRepo.findByPropertyUserId(user.getId());
        return reviews;
    }

    @GetMapping("/{propertyId}")
    @ResponseStatus(HttpStatus.OK)
    public ReviewDto getAllReviewsForProperty(@PathVariable long propertyId) {
        Optional<Property> opProperty = propertyRepository.findById(propertyId);
        if (opProperty.isPresent()) {
            Property property = opProperty.get();
            List<Review> reviewList = reviewRepo.findByProperty(property);
            int sum = reviewList.stream().mapToInt(Review::getRating).sum();
            double rating = (double) sum / reviewList.size();
            System.out.println(rating);
            ReviewDto dto=new ReviewDto();
            dto.setOverallRating(rating);
            dto.setReviewList(reviewList);
            return dto;
        }
            return null;

        }

}

