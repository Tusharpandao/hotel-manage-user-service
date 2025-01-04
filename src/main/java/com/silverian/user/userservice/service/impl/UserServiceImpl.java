package com.silverian.user.userservice.service.impl;

import com.silverian.user.userservice.entity.Hotel;
import com.silverian.user.userservice.entity.Rating;
import com.silverian.user.userservice.entity.User;
import com.silverian.user.userservice.exceptions.ResourceNotFoundException;
import com.silverian.user.userservice.external.service.HotelService;
import com.silverian.user.userservice.repository.UserRepository;
import com.silverian.user.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {

        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }



    @Override
    public List<User> getAllUser() {
        List<User> users = userRepository.findAll();

        users.forEach(user -> {
            try {
                // Fetch ratings for the user
                Rating[] ratingsOfUser = restTemplate.getForObject(
                        "http://RATING-SERVICE/ratings/user/" + user.getUserId(), Rating[].class);

                if (ratingsOfUser != null) {
                    // Convert ratings array to list
                    List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

                    // Fetch and set hotel details for each rating
                    ratings.forEach(rating -> {
                        try {
                            String hotelUrl = "http://HOTEL-SERVICE/hotel/" + rating.getHotelId();
                            ResponseEntity<Hotel> response = restTemplate.getForEntity(hotelUrl, Hotel.class);
                            Hotel hotel = response.getBody();
                            rating.setHotel(hotel);
                        } catch (Exception e) {
                            System.err.println("Failed to fetch hotel details for hotelId: " + rating.getHotelId() + " - " + e.getMessage());
                        }
                    });

                    user.setRatings(ratings);
                } else {
                    user.setRatings(new ArrayList<>());
                }
            } catch (Exception e) {
                System.err.println("Failed to fetch ratings for user: " + user.getUserId() + " - " + e.getMessage());
                user.setRatings(new ArrayList<>());
            }
        });

        return users;
    }


    @Override
    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + user.getUserId() + " not found."));

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setAbout(user.getAbout());

        return userRepository.save(existingUser);
    }

    @Override
    public User deleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found."));
        userRepository.delete(user);

        return user;
    }


    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found."));

        //fetch rating of the above user from RATING SERVICE
        Rating[] ratingsOfUsers = restTemplate.getForObject("http://RATING-SERVICE/ratings/user/" + user.getUserId(), Rating[].class);

        assert ratingsOfUsers != null;
        List<Rating> ratings = Arrays.stream(ratingsOfUsers).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
//            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotel/" + rating.getHotelId(), Hotel.class);
//            Hotel hotel = forEntity.getBody();
            Hotel hotel = hotelService.getHotel(rating.getHotelId());

            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);

//        logger.info(""+ratings);

        return user;
    }


}

//@Override
//public List<User> getAllUser() {
//    List<User> users = userRepository.findAll();
//
//    users.forEach(user -> {
//        try {
//            Rating [] ratingsOfUser = restTemplate.getForObject(
//                    "http://RATING-SERVICE/ratings/user/" + user.getUserId(), Rating[].class);
//
//            assert ratingsOfUser != null;
//            List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
//
//            // Map ratings to include hotel information
//
//            List<Rating> ratingList = ratings.stream().peek(rating -> {
//                String hotelUrl = "http://HOTEL-SERVICE/hotel/" + rating.getHotelId();
//                ResponseEntity<Hotel> forEntity = restTemplate.getForEntity(hotelUrl, Hotel.class);
//                Hotel hotel = forEntity.getBody();
//
//                rating.setHotel(hotel);
//
//            }).collect(Collectors.toList());
//
//            user.setRatings(ratings);
//        } catch (Exception e) {
//            // Log the error and set an empty list of ratings for the user
//            System.err.println("Failed to fetch ratings for user: " + user.getUserId() + " - " + e.getMessage());
//            user.setRatings(new ArrayList<>());
//        }
//    });
//
//
//    return users;
//}