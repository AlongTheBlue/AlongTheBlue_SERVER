package org.alongtheblue.alongtheblue_server.global.data.accommodation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation,String> {
    List<Accommodation> findByIntroductionIsNull();
    @Query("SELECT a FROM Accommodation a WHERE a.introduction IS NULL")
    List<Accommodation> findAccommodationsWithNullIntroduction();
    @Query("SELECT a.contentsid FROM Accommodation a")
    List<String> findAllContentsIds();
}

