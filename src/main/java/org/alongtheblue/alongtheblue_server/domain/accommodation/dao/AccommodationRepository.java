package org.alongtheblue.alongtheblue_server.domain.accommodation.dao;

import org.alongtheblue.alongtheblue_server.domain.accommodation.domain.Accommodation;
import org.alongtheblue.alongtheblue_server.domain.accommodation.dto.AccommodationDTO;
import org.alongtheblue.alongtheblue_server.domain.global.SimpleInformation;
import org.alongtheblue.alongtheblue_server.domain.search.domain.SearchInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    @Query("SELECT a FROM Accommodation a JOIN a.images i GROUP BY a HAVING COUNT(i) > 0")
    Page<SimpleInformation> findAllSimple(Pageable pageable);

    List<Accommodation> findByIntroductionIsNull();
    @Query("SELECT a FROM Accommodation a WHERE a.introduction IS NULL")
    List<Accommodation> findAccommodationsWithNullIntroduction();
    @Query("SELECT a.contentId FROM Accommodation a")
    List<String> findAllContentId();

    Accommodation findByContentId(String contentId);

    @Query(value = "SELECT a.* " +
            "FROM accommodation a " +
            "JOIN accommodation_image ai ON a.contentId = ai.accommodation " +
            "GROUP BY a.contentId " +
            "HAVING COUNT(ai.url) > 0 " +
            "ORDER BY RAND() " +
            "LIMIT 6",
            nativeQuery = true)
    List<Accommodation> findRandomAccommodationsWithImages();

    @Query(value = "SELECT a.contentId, a.roadaddress, a.title, "
            + "(SELECT GROUP_CONCAT(ai.url SEPARATOR ',') FROM accommodation_image ai WHERE ai.accommodation = a.contentId) as imageUrls, "
            + "a.introduction "
            + "FROM accommodation a "
            + "JOIN accommodation_image ai ON a.contentId = ai.accommodation "
            + "GROUP BY a.contentId "
            + "HAVING COUNT(ai.url) > 0 "
            + "ORDER BY RAND()", nativeQuery = true)
    List<AccommodationDTO> findAccommodationsWithTwoImages();

    @Query("SELECT a FROM Accommodation a JOIN a.images i WHERE a.title LIKE %:keyword% GROUP BY a HAVING COUNT(i) > 0")
    Page<SearchInformation> findByTitleContaining(String keyword, Pageable pageable);
}

