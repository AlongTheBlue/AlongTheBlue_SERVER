package org.alongtheblue.alongtheblue_server.global.data.alongBlues;

import jakarta.persistence.Entity;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BlueCourseRepository extends JpaRepository<BlueCourse, Long> {
}
