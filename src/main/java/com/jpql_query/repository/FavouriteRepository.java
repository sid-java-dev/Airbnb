package com.jpql_query.repository;

import com.jpql_query.entity.Favourite;
import com.jpql_query.entity.Property;
import com.jpql_query.entity.PropertyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    @Query("select f FROM Favourite f WHERE f.property=:property AND f.propertyUser=:propertyUser")
    Favourite findFavourite(@Param("property") Property property, @Param("propertyUser") PropertyUser propertyUser);

    @Query("SELECT f FROM Favourite f WHERE f.isFavourite = true AND f.propertyUser = :propertyUser")
    List<Favourite> findFavouriteProperty(@Param("propertyUser") PropertyUser propertyUser);
}