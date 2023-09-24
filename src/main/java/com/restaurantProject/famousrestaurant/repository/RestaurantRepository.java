package com.restaurantProject.famousrestaurant.repository;

import com.restaurantProject.famousrestaurant.dto.Member;
import com.restaurantProject.famousrestaurant.entity.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    RestaurantEntity findByRestaurantName(String name);

    List<RestaurantEntity> findByCategory(String category);

    Page<RestaurantEntity> findByCategory(String category, PageRequest id);


}
