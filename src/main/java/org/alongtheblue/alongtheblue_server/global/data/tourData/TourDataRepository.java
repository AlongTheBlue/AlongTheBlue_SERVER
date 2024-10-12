package org.alongtheblue.alongtheblue_server.global.data.tourData;

import org.alongtheblue.alongtheblue_server.global.data.accommodation.Accommodation;
import org.alongtheblue.alongtheblue_server.global.data.tourcommunity.UserTourCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TourDataRepository extends JpaRepository<TourData, Long> {

    // TourImageEntity를 저장할 메서드 추가

//    TourData findByContentId(String contentId);

    @Query(value = "SELECT a.* " +
            "FROM tour_data a " +
            "JOIN tour_data_image ai ON a.contentsid = ai.tour_data " +
            "GROUP BY a.id " +
            "HAVING COUNT(ai.url) > 0 " +
            "ORDER BY RAND() " +
            "LIMIT 6",
            nativeQuery = true)
     List<TourData> findRandomTourDatasWithImages();

    List<TourData> findByTitleContaining(String keyword);

    Optional<TourData> findByContentId(String contentsid);
}
