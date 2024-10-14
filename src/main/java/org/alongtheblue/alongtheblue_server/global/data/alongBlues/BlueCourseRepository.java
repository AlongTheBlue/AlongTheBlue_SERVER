package org.alongtheblue.alongtheblue_server.global.data.alongBlues;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BlueCourseRepository extends JpaRepository<BlueCourse, Long> {

    List<BlueCourse> findByUserInfo_Uid(String uid);
}
