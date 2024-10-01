package org.alongtheblue.alongtheblue_server.global.data.alongBlues;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlueCourseService {
    @Autowired
    private final BlueCourseRepository blueCourseRepository;
    @Autowired
    private final BlueItemRepository blueItemRepository;

    public BlueCourseService(BlueCourseRepository blueCourseRepository, BlueItemRepository blueItemRepository) {
        this.blueCourseRepository = blueCourseRepository;
        this.blueItemRepository = blueItemRepository;
    }

    public void createCourse(BlueCourse blueCourse) {
        blueCourseRepository.save(blueCourse);
        blueItemRepository.saveAll(blueCourse.getBlueItems());
    }

    public List<BlueCourse> getAllCourse() {
        return blueCourseRepository.findAll();
    }

    public BlueCourse getCourse(Long id) {
        Optional<BlueCourse>course= blueCourseRepository.findById(id);
        return course.orElse(null);
    }


}
