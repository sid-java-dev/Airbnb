package com.jpql_query.repository;

import com.jpql_query.entity.Booking;
import com.jpql_query.entity.Property;
import com.jpql_query.entity.PropertyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface BookingRepository extends JpaRepository<Booking, Long> {
@Query("Select b from Booking b where b.checkIn=:checkIn AND b.checkOut=:checkOut And b.property=:property")
    Booking findByCheckInAndCheckOut(@Param("checkIn") LocalDate checkIn, @Param("checkOut") LocalDate checkOut,@Param("property") Property property);
}