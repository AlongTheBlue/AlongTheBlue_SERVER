package org.alongtheblue.alongtheblue_server.global.data.cafe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
    @Query("SELECT c.contentId AS contentId, c.title AS title, c.addr AS address, c.images AS images FROM Cafe c")
    Page<CafeSimpleInformation> findAllSimple(Pageable pageable);

    List<Cafe> findByTitleContaining(String keyword);

    Optional<Cafe> findByContentId(String contentId);
}
