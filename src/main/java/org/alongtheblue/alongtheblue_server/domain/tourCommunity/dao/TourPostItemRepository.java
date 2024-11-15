package org.alongtheblue.alongtheblue_server.domain.tourCommunity.dao;

import org.alongtheblue.alongtheblue_server.domain.tourCommunity.domain.UserTourCourse;
import org.alongtheblue.alongtheblue_server.domain.tourCommunity.domain.TourPostItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourPostItemRepository extends JpaRepository<TourPostItem, Long> {
    List<TourPostItem> findByUserTourCourse(UserTourCourse userCourse);
}
