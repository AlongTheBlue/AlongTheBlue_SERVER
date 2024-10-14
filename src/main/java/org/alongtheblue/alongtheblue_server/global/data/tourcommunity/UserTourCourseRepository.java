package org.alongtheblue.alongtheblue_server.global.data.tourcommunity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTourCourseRepository extends JpaRepository<UserTourCourse, Long> {

    List<UserTourCourse> findByUserInfo_Uid(String uid);
}
