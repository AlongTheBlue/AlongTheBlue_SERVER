package org.alongtheblue.alongtheblue_server.domain.course.dao;

import org.alongtheblue.alongtheblue_server.domain.course.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
