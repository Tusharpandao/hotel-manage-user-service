package com.silverian.user.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rating {

    private String ratingId;
    private String userid;
    private String hotelId;
    private double ratingValue;
    private String feedback;
    private  Hotel hotel;
}
