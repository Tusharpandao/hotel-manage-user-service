package com.silverian.user.userservice.external.service;

import com.silverian.user.userservice.entity.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "RATING-SERVICE")
public interface RatingService {
    @GetMapping("/ratings/user/{userId}")
    List<Rating> getRatingOfUser(@PathVariable String userId);


}
