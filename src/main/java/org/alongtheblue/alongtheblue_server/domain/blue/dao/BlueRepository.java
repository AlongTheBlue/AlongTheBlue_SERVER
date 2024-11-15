package org.alongtheblue.alongtheblue_server.domain.blue.dao;

import org.alongtheblue.alongtheblue_server.domain.blue.domain.Blue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlueRepository extends JpaRepository<Blue, Long> {
    List<Blue> findByNameContaining(String name);
}
