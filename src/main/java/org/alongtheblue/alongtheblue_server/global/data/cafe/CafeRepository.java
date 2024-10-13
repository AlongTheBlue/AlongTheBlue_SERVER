package org.alongtheblue.alongtheblue_server.global.data.cafe;

import org.alongtheblue.alongtheblue_server.global.data.global.SimpleInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CafeRepository extends JpaRepository<Cafe, Long> {

    @Query("SELECT c FROM Cafe c JOIN c.images i GROUP BY c HAVING COUNT(i) > 0")
    Page<SimpleInformation> findAllSimple(Pageable pageable);

    List<Cafe> findByTitleContaining(String keyword);

    Optional<Cafe> findByContentId(String contentId);
}
