package com.silverian.user.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
