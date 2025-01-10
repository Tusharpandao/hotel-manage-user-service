package com.silverian.user.userservice.controller;

import com.silverian.user.userservice.entity.User;
import com.silverian.user.userservice.service.UserService;
import com.silverian.user.userservice.service.impl.UserServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);


    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {

        User user1 = userService.saveUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    // Add more endpoints for other operations like update, delete, etc.
    @GetMapping("/{userId}")
    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getUser(@PathVariable String userId) {

        User user = userService.getUser(userId);

        return ResponseEntity.ok(user);
    }

    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex) {
        logger.info("fallback is executed because service is down : ", ex.getMessage());

        User user = User.builder()
                .userId("123")
                .about("this user is dummy user ")
                .name("dummy user")
                .email("dummy@gmail.com")
                .build();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers() {

        List<User> users = userService.getAllUser();

        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable String userId) {

        User user = userService.deleteUser(userId);

        return ResponseEntity.ok(user);
    }

    @PatchMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {

        User updatedUser = userService.updateUser(user);

        return ResponseEntity.ok(updatedUser);
    }

}
