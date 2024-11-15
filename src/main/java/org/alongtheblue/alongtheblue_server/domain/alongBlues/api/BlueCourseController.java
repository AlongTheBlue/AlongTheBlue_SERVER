package org.alongtheblue.alongtheblue_server.domain.alongBlues.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.domain.alongBlues.dto.response.BlueCourseDto;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.alongtheblue.alongtheblue_server.domain.alongBlues.application.BlueCourseService;
import org.alongtheblue.alongtheblue_server.domain.alongBlues.domain.BlueCourse;
import org.alongtheblue.alongtheblue_server.domain.alongBlues.dto.request.CreateBlueCourseRequestDto;
import org.alongtheblue.alongtheblue_server.domain.alongBlues.dto.response.BlueCourseResponseDto;
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
    public ApiResponse<BlueCourseDto> getBlueCourse(@PathVariable Long id){
        return blueCourseService.getBlueCourse(id);
    }

    @Operation(summary = "내 바당따라 전체 코스 조회 API")
    @GetMapping("/my")
    public ApiResponse<List<BlueCourseResponseDto>> retrieveMyBlueCourses(@RequestHeader("Authorization") String uid) {
        return blueCourseService.retrieveMyBlueCourses(uid);
    }
}
