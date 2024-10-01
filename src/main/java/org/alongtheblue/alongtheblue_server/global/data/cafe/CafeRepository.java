package org.alongtheblue.alongtheblue_server.global.data.cafe;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeRepository extends JpaRepository<Cafe, Long> {

    List<Cafe> findByTitleContaining(String keyword);
}
