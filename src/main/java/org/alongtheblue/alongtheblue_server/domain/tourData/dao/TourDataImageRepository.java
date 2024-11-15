package org.alongtheblue.alongtheblue_server.domain.tourData.dao;

import org.alongtheblue.alongtheblue_server.domain.tourData.domain.TourDataImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourDataImageRepository extends JpaRepository<TourDataImage, Long> {

}
