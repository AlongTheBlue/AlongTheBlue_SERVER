package org.alongtheblue.alongtheblue_server.global.data.cafe;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CafeRepository extends JpaRepository<Cafe, Long> {

    List<Cafe> findByTitleContaining(String keyword);

    Optional<Cafe> findByContentId(String contentId);
}
