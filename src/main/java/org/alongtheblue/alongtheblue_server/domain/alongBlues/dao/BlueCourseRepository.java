package org.alongtheblue.alongtheblue_server.domain.alongBlues.dao;

import org.alongtheblue.alongtheblue_server.domain.alongBlues.domain.BlueCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BlueCourseRepository extends JpaRepository<BlueCourse, Long> {

    List<BlueCourse> findByUserInfo_Uid(String uid);
}
