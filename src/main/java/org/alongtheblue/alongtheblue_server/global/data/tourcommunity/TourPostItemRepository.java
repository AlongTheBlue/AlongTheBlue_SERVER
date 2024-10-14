package org.alongtheblue.alongtheblue_server.global.data.tourcommunity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourPostItemRepository extends JpaRepository<TourPostItem, Long> {
    List<TourPostItem> findByUserTourCourse(UserTourCourse userCourse);
}
