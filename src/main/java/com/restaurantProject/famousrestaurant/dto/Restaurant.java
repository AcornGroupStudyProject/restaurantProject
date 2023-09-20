package com.restaurantProject.famousrestaurant.dto;

import com.restaurantProject.famousrestaurant.entity.RestaurantEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    private Long id;
    private String restaurantName;
    private String restaurantAddress;
    private String restaurantRoadAddress;
    private String category;
    private double xCoordinate;
    private double yCoordinate;

    public static Restaurant toRestaurant(RestaurantEntity restaurantEntity){
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantEntity.getId());
        restaurant.setRestaurantName(restaurantEntity.getRestaurantName());
        restaurant.setRestaurantAddress(restaurantEntity.getRestaurantAddress());
        restaurant.setRestaurantRoadAddress(restaurantEntity.getRestaurantRoadAddress());
        restaurant.setXCoordinate(restaurantEntity.getXCoordinate());
        restaurant.setYCoordinate(restaurantEntity.getYCoordinate());
        restaurant.setCategory(restaurantEntity.getCategory());
        return restaurant;
    }

}
