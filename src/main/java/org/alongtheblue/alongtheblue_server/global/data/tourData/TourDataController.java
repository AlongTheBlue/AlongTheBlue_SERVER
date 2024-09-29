package org.alongtheblue.alongtheblue_server.global.data.tourData;

import org.alongtheblue.alongtheblue_server.global.data.accommodation.AccommodationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TourDataController {
    @Autowired
    private TourDataService tourDataService;

    @GetMapping("/tourData")
    public ArrayList<TourData> getTourData() {
        return tourDataService.getTourData();
    }

    @GetMapping("/tourData/overview")
    public List<TourData> getTourDataOverview() {
        return tourDataService.updateOverviews();
    }

    @GetMapping("/tourData/introduction")
    public List<TourData> getTourDataIntroduction() {
        return tourDataService.updateIntroduction();
    }
    @GetMapping("/home/tourData")
    public ArrayList<TourData> getHomeTourData() {
        return tourDataService.getHomeTourData();
    }

    @GetMapping("/tourData/{contentsid}") // 상세보기
    public TourDataBasicDto gettourData(@PathVariable String contentsid) {
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
