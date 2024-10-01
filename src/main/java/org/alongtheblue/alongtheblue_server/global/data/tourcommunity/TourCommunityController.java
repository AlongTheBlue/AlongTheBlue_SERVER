package org.alongtheblue.alongtheblue_server.global.data.tourcommunity;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/tourpost")
@RequiredArgsConstructor
public class TourCommunityController {

    private final TourCommunityService tourCommunityService;

    @PostMapping
    public UserTourCourse createTourCourse(
            @RequestPart("title") String title,
            @RequestPart("writing") String writing,
            @RequestPart("createdate") Date date,
            @RequestPart List<TourPostItem> tourItems,
            @RequestPart List<TourPostHashTag> hashTags,
            @RequestPart List<MultipartFile> images,
            @RequestPart List<List<Integer>> index) {


        UserTourCourse userTourCourse= new UserTourCourse();
        userTourCourse.setTitle(title);
        userTourCourse.setTourPostItems(tourItems);
        userTourCourse.setTourPostHashTags(hashTags);
        userTourCourse.setCreatedate(date);
        userTourCourse.setWriting(writing);

        return tourCommunityService.createPost(userTourCourse, images, index);
    }


    @GetMapping("/allpost")
    public List<TourDTO> allPost(){
        return tourCommunityService.allPost();
    }

    @GetMapping("/{postid}")
    public TourDTO onepost(@PathVariable Long postid){
        return tourCommunityService.onepost(postid);
    }











}
