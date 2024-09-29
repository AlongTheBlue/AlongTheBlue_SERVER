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











}
