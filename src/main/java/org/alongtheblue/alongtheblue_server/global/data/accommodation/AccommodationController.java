package org.alongtheblue.alongtheblue_server.global.data.accommodation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accommodation")
public class AccommodationController {
    @Autowired
    private AccommodationService accommodationService;

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
        accommodationService.updateAccommodationsWithNullIntroduction();
        return ResponseEntity.ok("Accommodation info updated successfully.");
    }

}
