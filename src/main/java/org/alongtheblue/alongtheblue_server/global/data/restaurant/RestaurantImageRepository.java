package org.alongtheblue.alongtheblue_server.global.data.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantImageRepository extends JpaRepository<RestaurantImage, Long> {
    List<RestaurantImage> findByrestaurant(Restaurant restaurant);
}
