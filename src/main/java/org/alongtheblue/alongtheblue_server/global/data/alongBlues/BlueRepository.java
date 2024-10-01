package org.alongtheblue.alongtheblue_server.global.data.alongBlues;

import org.alongtheblue.alongtheblue_server.global.data.blue.Blue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlueRepository extends JpaRepository<Blue, Long> {
    List<Blue> findByNameContaining(String name);
}
