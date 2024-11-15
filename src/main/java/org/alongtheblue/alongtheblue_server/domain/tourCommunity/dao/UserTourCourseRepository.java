package org.alongtheblue.alongtheblue_server.domain.tourCommunity.dao;

import org.alongtheblue.alongtheblue_server.domain.tourCommunity.domain.UserTourCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTourCourseRepository extends JpaRepository<UserTourCourse, Long> {

    List<UserTourCourse> findByUserInfo_Uid(String uid);
}
