package org.alongtheblue.alongtheblue_server.global.data.accommodation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation,String> {
    List<Accommodation> findByIntroductionIsNull();
    @Query("SELECT a FROM Accommodation a WHERE a.introduction IS NULL")
    List<Accommodation> findAccommodationsWithNullIntroduction();
    @Query("SELECT a.contentsid FROM Accommodation a")
    List<String> findAllContentsIds();

    Accommodation findByContentsid(String contentsid);

    @Query(value = "SELECT a.* " +
            "FROM accommodation a " +
            "JOIN accommodation_image ai ON a.contentsid = ai.accommodation " +
            "GROUP BY a.contentsid " +
            "HAVING COUNT(ai.url) > 0 " +
            "ORDER BY RAND() " +
            "LIMIT 6",
            nativeQuery = true)
    List<Accommodation> findRandomAccommodationsWithImages();

    @Query(value = "SELECT a.contentsid, a.roadaddress, a.title, "
            + "(SELECT GROUP_CONCAT(ai.url SEPARATOR ',') FROM accommodation_image ai WHERE ai.accommodation = a.contentsid) as imageUrls, "
            + "a.introduction "
            + "FROM accommodation a "
            + "JOIN accommodation_image ai ON a.contentsid = ai.accommodation "
            + "GROUP BY a.contentsid "
            + "HAVING COUNT(ai.url) > 0 "
            + "ORDER BY RAND()", nativeQuery = true)
    List<AccommodationDTO> findAccommodationsWithTwoImages();
}

