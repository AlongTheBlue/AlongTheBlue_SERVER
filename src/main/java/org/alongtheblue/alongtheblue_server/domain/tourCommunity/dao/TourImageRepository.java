package org.alongtheblue.alongtheblue_server.domain.tourCommunity.dao;

import org.alongtheblue.alongtheblue_server.domain.tourCommunity.domain.TourImage;
import org.alongtheblue.alongtheblue_server.domain.tourCommunity.domain.TourPostItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourImageRepository extends JpaRepository<TourImage, Long> {
    List<TourImage> findBytourPostItem(TourPostItem tourPostItem);
}
