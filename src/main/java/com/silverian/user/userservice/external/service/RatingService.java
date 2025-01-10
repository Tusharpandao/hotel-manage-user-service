package com.silverian.user.userservice.external.service;

import com.silverian.user.userservice.entity.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "RATING-SERVICE")
@Service
public interface RatingService {
    // get ratings
    @GetMapping("/ratings/user/{userId}")
    List<Rating> getRatingOfUser(@PathVariable String userId);

    // add rating
     @PostMapping("/ratings")
    Rating addRating(Rating rating);


    // delete rating
    @DeleteMapping("/ratings/{ratingId}")
    void deleteRating(@PathVariable String ratingId);




}
