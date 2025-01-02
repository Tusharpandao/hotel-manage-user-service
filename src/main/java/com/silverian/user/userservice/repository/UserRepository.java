package com.silverian.user.userservice.repository;

import com.silverian.user.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {


}
