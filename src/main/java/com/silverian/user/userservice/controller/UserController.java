package com.silverian.user.userservice.controller;

import com.silverian.user.userservice.entity.User;
import com.silverian.user.userservice.service.UserService;
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


    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user){

        User user1 = userService.saveUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    // Add more endpoints for other operations like update, delete, etc.
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId){

        User user = userService.getUser(userId);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers(){

        List<User> users = userService.getAllUser();

        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable String userId){

        User user = userService.deleteUser(userId);

        return ResponseEntity.ok(user);
    }

    @PatchMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user){

        User updatedUser = userService.updateUser(user);

        return ResponseEntity.ok(updatedUser);
    }

}
