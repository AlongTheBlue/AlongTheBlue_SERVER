package org.alongtheblue.alongtheblue_server.global.data.tourData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
