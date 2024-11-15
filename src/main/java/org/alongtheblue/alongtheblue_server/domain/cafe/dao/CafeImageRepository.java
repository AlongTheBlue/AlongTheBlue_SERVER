package org.alongtheblue.alongtheblue_server.domain.cafe.dao;

import org.alongtheblue.alongtheblue_server.domain.cafe.domain.Cafe;
import org.alongtheblue.alongtheblue_server.domain.cafe.domain.CafeImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeImageRepository extends JpaRepository<CafeImage, Long> {
    List<CafeImage> findBycafe(Cafe cafe);
}
