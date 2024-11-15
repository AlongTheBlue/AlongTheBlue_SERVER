package org.alongtheblue.alongtheblue_server.domain.restaurant.dao;

import org.alongtheblue.alongtheblue_server.domain.restaurant.domain.Restaurant;
import org.alongtheblue.alongtheblue_server.domain.restaurant.domain.RestaurantImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantImageRepository extends JpaRepository<RestaurantImage, Long> {
    List<RestaurantImage> findByrestaurant(Restaurant restaurant);
}
