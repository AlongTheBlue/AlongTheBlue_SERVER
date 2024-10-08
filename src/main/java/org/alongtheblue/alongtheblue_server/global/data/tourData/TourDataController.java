package org.alongtheblue.alongtheblue_server.global.data.tourData;

import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tourData")
public class TourDataController {
    @Autowired
    private TourDataService tourDataService;

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
    @GetMapping("/home")
    public ApiResponse<List<TourDataDto>> getHomeTourData() {
        return tourDataService.getHomeTourData();
    }

    @GetMapping("/{contentsid}") // 상세보기
    public TourDataDto getTourDataById(@PathVariable String contentsid) {
        return tourDataService.getTourDataDetails(contentsid);
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
