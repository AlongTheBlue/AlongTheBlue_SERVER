package org.alongtheblue.alongtheblue_server.global.data.tourData;

import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.alongtheblue.alongtheblue_server.global.data.global.SimpleInformation;
import org.alongtheblue.alongtheblue_server.global.data.global.dto.response.DetailResponseDto;
import org.alongtheblue.alongtheblue_server.global.data.global.dto.response.HomeResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tourData")
public class TourDataController {

    private final TourDataService tourDataService;

    @GetMapping("/save")
    public ArrayList<TourData> getTourData() {
        return tourDataService.getTourData();
    }

    @GetMapping("/save/overview")
    public List<TourData> getTourDataOverview() {
        return tourDataService.updateOverviews();
    }

    @GetMapping("/save/info")
    public List<TourData> getTourDataIntroduction() {
        return tourDataService.updateInfo();
    }
    @GetMapping("save/images")
    public void getTourDataImages(){
        tourDataService.updateAllTourDataImageUrls();
    }


    @GetMapping("/detail/all")
    public ApiResponse<Page<SimpleInformation>> retrieveAll(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        return tourDataService.retrieveAll(page, size);
    }

    @GetMapping("/home")
    public ApiResponse<List<TourDataDto>> getHomeTourData() {
        return tourDataService.getHomeTourData();
    }

    @GetMapping("/{contentsid}") // 상세보기
    public TourDataDto getTourDataById(@PathVariable String contentsid) {
        return tourDataService.getTourDataDetails(contentsid);
    }

    @GetMapping("/home/list")
    public ApiResponse<List<HomeResponseDto>> getHomeTourDataList() {
        return tourDataService.getHomeTourDataList();
    }

    @GetMapping("/detail/{id}")
    public ApiResponse<DetailResponseDto> getTourDataDetail(@PathVariable String id){
        return tourDataService.getTourDataDetail(id);
//        return restaurantService.getRestaurant(id);
    }

    @GetMapping("/hashtags/{id}")
    public ApiResponse<List<String>> getHashtagsById(@PathVariable String id) {
        return tourDataService.getHashtagsById(id);
    }

//    @GetMapping("/random-details")
//    public ResponseEntity<List<TourDataBasicDto>> getRandomTourDataDetails() {
////        List<TourDataBasicDto> tourDataList = tourDataService.getRandomTourDataDetailsWithImages();
////
////        if (tourDataList.isEmpty()) {
////            return ResponseEntity.noContent().build(); // 데이터가 없을 경우 204 No Content
////        }
//
//        return ResponseEntity.ok(tourDataList); // 200 OK와 함께 데이터 반환
//    }

}
