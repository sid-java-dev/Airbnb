package com.jpql_query.repository;

import com.jpql_query.entity.Property;
import com.jpql_query.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
   @Query("select r from Review r where r.property.id=:propertyId AND r.propertyUser.id=:userId" )
   Review findByReviewByPropertyIdAndPropertyUserId(@Param("propertyId") Long propertyId,@Param("userId") Long userId);
   List<Review>findByPropertyUserId(Long userId);

   List<Review> findByProperty(Property property);
}