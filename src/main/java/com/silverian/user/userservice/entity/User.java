package com.silverian.user.userservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "micro_users")
public class User {
    @Id
    @Column(name = "ID")
    private String userId;
    private String name;
    private String email;
    private String about;

    @Transient
    List<Rating> ratings=new ArrayList<>();
}
