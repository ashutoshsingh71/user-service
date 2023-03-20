package com.ashu.userservice.external.services;

import com.ashu.userservice.entity.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(name = "RATING-SERVICE")
public interface RatingService {

    @PostMapping("/ratings")
    ResponseEntity<Rating> createRating(Rating rating);

    @PutMapping()
    ResponseEntity<Rating> updateRating(Rating rating);
    @GetMapping("/ratings")
    ResponseEntity<List<Rating>> getAllRatings();
}
