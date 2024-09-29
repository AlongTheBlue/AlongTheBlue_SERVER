package org.alongtheblue.alongtheblue_server.global.data.tourData;

import org.alongtheblue.alongtheblue_server.global.data.tourcommunity.TourImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourDataImageRepository extends JpaRepository<TourDataImage, Long> {

}
