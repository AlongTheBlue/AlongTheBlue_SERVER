package org.alongtheblue.alongtheblue_server.global.data.cafe;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeImageRepository extends JpaRepository<CafeImage, Long> {
    List<CafeImage> findBycafe(Cafe cafe);
}
