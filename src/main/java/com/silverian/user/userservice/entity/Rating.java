package com.silverian.user.userservice.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rating {

    private String ratingId;
    private String userId;
    private String hotelId;
    private double ratingValue;
    private String feedback;
    private  Hotel hotel;
}
