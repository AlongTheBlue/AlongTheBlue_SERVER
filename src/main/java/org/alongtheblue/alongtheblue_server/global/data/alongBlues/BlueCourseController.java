package org.alongtheblue.alongtheblue_server.global.data.alongBlues;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/blueCourse")
public class BlueCourseController {
    private final BlueCourseService blueCourseService;
    @PostMapping("/create")
    public void createCourse(@RequestBody BlueCourse blueCourse){
        blueCourseService.createCourse(blueCourse);
    }

    @GetMapping("/allcourse")
    public List<BlueCourse> getAllCourse(){
        return blueCourseService.getAllCourse();
    }

    @GetMapping("/{courseid}")
    public BlueCourse getCourse(@PathVariable Long id){
        return blueCourseService.getCourse(id);
    }
}
