package org.alongtheblue.alongtheblue_server.global.data.course;

import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/course")
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/home/list")
    public ApiResponse<List<CourseHomeDto>> getHomeTourCourses() {
        return courseService.getHomeCourse();
    }

    @GetMapping("/detail/{id}")
    public ApiResponse<CourseDetailDto> getDetailTourCourse(@PathVariable Long id) {
        return courseService.getDetailCourse(id);
    }

    @PostMapping
    public ApiResponse<String> saveTourCourse(@RequestPart CourseRequestDto tourCourseRequestDto) {
        return courseService.saveCourse(tourCourseRequestDto);
    }
}
