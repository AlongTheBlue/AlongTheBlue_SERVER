package org.alongtheblue.alongtheblue_server.domain.cafe.dao;

import org.alongtheblue.alongtheblue_server.domain.cafe.domain.Cafe;
import org.alongtheblue.alongtheblue_server.domain.global.SimpleInformation;
import org.alongtheblue.alongtheblue_server.domain.search.domain.SearchInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CafeRepository extends JpaRepository<Cafe, Long> {

    @Query("SELECT c FROM Cafe c JOIN c.images i GROUP BY c HAVING COUNT(i) > 0")
    Page<SimpleInformation> findAllSimple(Pageable pageable);

    @Query("SELECT c FROM Cafe c JOIN c.images i WHERE c.title LIKE %:keyword% GROUP BY c HAVING COUNT(i) > 0")
    Page<SearchInformation> findByTitleContaining(String keyword, Pageable pageable);

    Optional<Cafe> findByContentId(String contentId);
}
