package com.silverian.user.userservice.service;

import com.silverian.user.userservice.entity.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);
    
    User getUser(String userId);

    User updateUser(User user);

    User deleteUser(String userId);

    List<User> getAllUser();



}

