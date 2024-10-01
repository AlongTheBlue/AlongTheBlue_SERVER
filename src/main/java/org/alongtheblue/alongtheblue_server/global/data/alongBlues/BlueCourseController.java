package org.alongtheblue.alongtheblue_server.global.data.alongBlues;

import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.alongtheblue.alongtheblue_server.global.data.alongBlues.dto.request.CreateBlueCourseRequestDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/blueCourse")
public class BlueCourseController {
    private final BlueCourseService blueCourseService;
    @PostMapping("/create")
    public ApiResponse<BlueCourse> createCourse(@RequestBody CreateBlueCourseRequestDto dto){
        return blueCourseService.createCourse(dto.toServiceRequest());
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
