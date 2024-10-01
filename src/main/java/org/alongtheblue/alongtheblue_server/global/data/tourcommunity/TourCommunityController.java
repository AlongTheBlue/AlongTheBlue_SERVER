package org.alongtheblue.alongtheblue_server.global.data.tourcommunity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.domain.userInfo.application.UserInfoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "여행따라 API", description = "여행따라  코스 등록 / 게시물 목록 조회 / 게시물 상세 조회")
@RestController
@RequestMapping("/api/tourpost")
@RequiredArgsConstructor
public class TourCommunityController {

    private final TourCommunityService tourCommunityService;

    private final UserInfoService userInfoService;

    @Operation(summary = "게시물 등록 API")
    @PostMapping
    public UserTourCourse createTourCourse(
            @RequestHeader("Authorization") String userId,
            @RequestPart(value = "request", required = false) TourCourseRequestDto dto,
            @RequestPart(value = "file", required = false) List<MultipartFile> images) {
        return tourCommunityService.createPost(userInfoService.retrieveUserInfo(userId).getData(), dto, images);
    }


    @Operation(summary = "전체 게시물 조회 API")
    @GetMapping("/allpost")
    public List<TourDTO> allPost(){
        return tourCommunityService.allPost();
    }

    @Operation(summary = "id로 게시물(여행코스 포함) 상세 조회")
    @GetMapping("/{postid}")
    public TourDTO onepost(@PathVariable Long postid){
        return tourCommunityService.onepost(postid);
    }

}
