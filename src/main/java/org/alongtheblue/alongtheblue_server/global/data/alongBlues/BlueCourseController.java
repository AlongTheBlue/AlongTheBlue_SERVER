package org.alongtheblue.alongtheblue_server.global.data.alongBlues;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/blue")
public class BlueCourseController {
    private final BlueService blueService;
    @PostMapping("/create")
    public void createCourse(@RequestBody BlueCourse blueCourse){
        blueService.createCourse(blueCourse);
    }

    @GetMapping("/allcourse")
    public List<BlueCourse> getAllCourse(){
        return blueService.getAllCourse();
    }

    @GetMapping("/{courseid}")
    public BlueCourse getCourse(@PathVariable Long id){
        return blueService.getCourse(id);
    }
}
