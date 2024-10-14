package org.alongtheblue.alongtheblue_server.global.data.alongBlues;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.alongtheblue.alongtheblue_server.global.data.alongBlues.dto.request.CreateBlueCourseRequestDto;
import org.alongtheblue.alongtheblue_server.global.data.alongBlues.dto.response.BlueCourseResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/blueCourse")
public class BlueCourseController {

    private final BlueCourseService blueCourseService;

    @PostMapping("/create")
    @Operation(summary = "바당따라 코스 추가")
    public ApiResponse<BlueCourse> createCourse(@RequestHeader("Authorization") String uid,
                                                @RequestBody CreateBlueCourseRequestDto dto){
        return blueCourseService.createCourse(uid, dto.toServiceRequest());
    }

    @GetMapping("/list")
    @Operation(summary = "바당따라 코스 전체 조회")
    public List<BlueCourse> getAllCourse(){
        return blueCourseService.getAllCourse();
    }

    @GetMapping("/{id}")
    @Operation(summary = "특정 바당따라 코스 조회")
    public ApiResponse<BlueCourseResponseDto> getBlueCourse(@PathVariable Long id){
        return blueCourseService.getBlueCourse(id);
    }
}
