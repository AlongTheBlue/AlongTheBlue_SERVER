package org.alongtheblue.alongtheblue_server.global.data.tourcommunity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourImageRepository extends JpaRepository<TourImage, Long> {
    List<TourImage> findBytourPostItem(TourPostItem tourPostItem);
}
