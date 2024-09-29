package org.alongtheblue.alongtheblue_server.global.data.tourcommunity;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tourpost")
@RequiredArgsConstructor
public class TourCommunityController {

    private final TourCommunityService tourCommunityService;

    @PostMapping("/createpost")
    public UserTourCourse createPost (@RequestBody UserTourCourse userTourCourse){
        return tourCommunityService.createPost(userTourCourse);
    }

    @GetMapping("/allpost")
    public List<UserTourCourse> allPost(){
        return tourCommunityService.allPost();
    }

    @GetMapping("/{postid}")
    public UserTourCourse onepost(@PathVariable Long postid){
        return tourCommunityService.onepost(postid);
    }

    @GetMapping("/updateImages/{contentId}")
    public ResponseEntity<String> updateOriginImageUrls(@PathVariable String contentId) {
        try {
            // TourCommunityService의 updateOriginImageUrls 메소드 호출
            tourCommunityService.updateOriginImageUrls(contentId);
            return ResponseEntity.ok("이미지 업데이트 성공");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("이미지 업데이트 중 오류 발생: " + e.getMessage());
        }
    }









}
