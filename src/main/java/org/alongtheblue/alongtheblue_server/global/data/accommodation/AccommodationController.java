package org.alongtheblue.alongtheblue_server.global.data.accommodation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accommodation")
public class AccommodationController {
    private final AccommodationService accommodationService;

    // visit jeju
    @GetMapping("/fetch-and-save")
    public String fetchAndSaveAccommodations() {
        accommodationService.saveAccommodations();
        return "Data fetched and saved successfully!";
    }

//    @GetMapping("/overview")
//    public String fetchANdUpdateOverview() {
//        accommodationService.updateOverview();
//        return "Data fetched and saved successfully";
//    }

    @GetMapping("/update")
    public ResponseEntity<String> updateAccommodationInfo() {
        accommodationService.updateOverview();
        return ResponseEntity.ok("Accommodation info updated successfully.");
    }

    @GetMapping("/home")
    public List<AccommodationDTO> getAccommodation(){
        return accommodationService.getAccommodationHomeInfo();
    }


    @GetMapping("/overview/1")
    public void updateOverview() {

        accommodationService.updateFirstOverviews();
    }

    @GetMapping("/overview/all")
    public void updateAllOverview() {

        accommodationService.updateAllOverviews();
    }

    @GetMapping("/checkintime")
    public void updateCheckInTimes(){

        accommodationService.updateCheckInTimes();
    }

    @GetMapping("/infocenter")
    public void updateInfoCenter(){

        accommodationService.updateInfoCenterLodgings();
    }

    @GetMapping("/imgurl")
    public void updateImgUrl(){

        accommodationService.updateAllOriginImageUrls();
    }

    @GetMapping("/{contentsid}") // 상세보기
    public AccommodationDTO getAccommodation(@PathVariable String contentsid) {
        return accommodationService.getAccommodationDetails(contentsid);
    }

    @GetMapping("/random") // 홈화면
    public ResponseEntity<List<AccommodationDTO>> getRandomAccommodationsWithImages() {
        List<AccommodationDTO> randomAccommodations = accommodationService.getRandomAccommodationDetailsWithImages();

        if (randomAccommodations.isEmpty()) {
            // 만약 결과가 없다면 404 Not Found 리턴
            return ResponseEntity.notFound().build();
        }

        // 성공적으로 데이터를 가져왔을 경우 200 OK와 함께 리스트 반환
        return ResponseEntity.ok(randomAccommodations);
    }

    @GetMapping("/with-images") // 세부
    public List<AccommodationDTO> getAccommodationsWithTwoImages() {
        return accommodationService.getRandomAccommodationDetailsWithTwoImages();
    }

    // 1단계 저장 100개만
    @GetMapping("/save")
    public void saveAccommodations() {
        accommodationService.saveAccommodations();
    }
    // 2단계 저장
    @GetMapping("/save/introduction")
    public void saveAccommodationsIntroduction() {
        accommodationService.processSaveIntroduction();
    }
    // 3단계 저장
    @GetMapping("/save/info")
    public void saveAccommodationsInfo() {
        accommodationService.processSaveInfo();
    }
    // 4단계 저장
    @GetMapping("/save/image")
    public void saveAccommodationsImages() {
        accommodationService.processSaveImage();
    }
}
