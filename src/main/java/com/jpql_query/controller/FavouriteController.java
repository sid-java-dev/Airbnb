package com.jpql_query.controller;

import com.jpql_query.entity.Favourite;
import com.jpql_query.entity.Property;
import com.jpql_query.entity.PropertyUser;
import com.jpql_query.repository.FavouriteRepository;
import com.jpql_query.repository.PropertyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/favourites")
public class FavouriteController {

    private PropertyRepository propertyRepository;
    private FavouriteRepository favouriteRepository;

    public FavouriteController(PropertyRepository propertyRepository, FavouriteRepository favouriteRepository) {
        this.propertyRepository = propertyRepository;
        this.favouriteRepository = favouriteRepository;
    }

    @PostMapping("/add/{propertyId}")
    public ResponseEntity<String> addFavourite(@PathVariable long propertyId,
                                               @AuthenticationPrincipal PropertyUser propertyUser) {
        if (propertyUser != null) {
            Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
            if (optionalProperty.isPresent()) {
                Property property = optionalProperty.get();
                Favourite f = favouriteRepository.findFavourite(property, propertyUser);
                if (f != null) {
                    return new ResponseEntity<>("property is already added to favorites", HttpStatus.BAD_REQUEST);
                }
                Favourite favourite = new Favourite();
                favourite.setProperty(property);
                favourite.setPropertyUser(propertyUser);
                favourite.setIsFavourite(true);
                favouriteRepository.save(favourite);
                return ResponseEntity.ok("Property added to favorites.");
            } else {
                return ResponseEntity.badRequest().body("Property not found.");
            }
        } else {
            return ResponseEntity.badRequest().body("User not authenticated.");
        }
    }
        @GetMapping("/properties")
        public List<Favourite> getAllFavourites (@AuthenticationPrincipal PropertyUser propertyUser) {
            List<Favourite> favourites = favouriteRepository.findFavouriteProperty(propertyUser);
            return favourites;
        }

}
