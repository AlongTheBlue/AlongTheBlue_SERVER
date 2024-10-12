package org.alongtheblue.alongtheblue_server.global.data.search;

import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.alongtheblue.alongtheblue_server.global.data.accommodation.AccommodationResponseDto;
import org.alongtheblue.alongtheblue_server.global.data.accommodation.AccommodationService;
import org.alongtheblue.alongtheblue_server.global.data.alongBlues.BlueService;
import org.alongtheblue.alongtheblue_server.global.data.blue.BlueResponseDto;
import org.alongtheblue.alongtheblue_server.global.data.cafe.CafeService;
import org.alongtheblue.alongtheblue_server.global.data.cafe.dto.PartCafeResponseDto;
import org.alongtheblue.alongtheblue_server.global.data.restaurant.RestaurantService;
import org.alongtheblue.alongtheblue_server.global.data.restaurant.dto.response.PartRestaurantResponseDto;
import org.alongtheblue.alongtheblue_server.global.data.tourData.TourDataService;
import org.alongtheblue.alongtheblue_server.global.data.tourData.dto.TourDataResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final BlueService blueService;
    private final RestaurantService restaurantService;
    private final SearchService searchService;
    private final CafeService cafeService;
    private final TourDataService tourDataService;
    private final AccommodationService accommodationService;

    @GetMapping("/blue/list")
    public ApiResponse<List<BlueResponseDto>> searchBlues() {
        return blueService.getBlues();
    }

    @GetMapping("/blue")
    public ApiResponse<List<BlueResponseDto>> searchBluesByKeyword(@RequestParam String keyword) {
        return blueService.getBluesByKeyword(keyword);
    }

    @GetMapping("/restaurant/list")
    public ApiResponse<List<PartRestaurantResponseDto>> searchRestaurants() {
        return restaurantService.homerestaurant();
    }

    @GetMapping("/restaurant")
    public ApiResponse<List<PartRestaurantResponseDto>> searchRestaurantsByKeyword(@RequestParam String keyword) {
        return restaurantService.getRestaurantsByKeyword(keyword);
    }

    @GetMapping("/all")
    public ApiResponse<List<SearchResponseDto>> searchAllCategoryByKeyword(@RequestParam String keyword){
        return searchService.searchAllCategoryByKeyword(keyword);
    }

    @GetMapping("/all/list")
    public ApiResponse<List<SearchResponseDto>> getAllCategory() {
        return searchService.searchAllCategory();
    }

    @GetMapping("/cafe/list")
    public ApiResponse<List<PartCafeResponseDto>> getCafesHome() {
        return cafeService.getCafesHome();
    }

    @GetMapping("/cafe")
    public ApiResponse<List<PartCafeResponseDto>> searchCafesByKeyword(@RequestParam String keyword) {
        return cafeService.getCafesByKeyword(keyword);
    }

    @GetMapping("/tourData/list")
    public ApiResponse<List<TourDataResponseDto>> getTourDataList() {
        return tourDataService.getTourDataListHome();
    }

    @GetMapping("/tourData")
    public ApiResponse<List<TourDataResponseDto>> getTourDataListByKeyword(@RequestParam String keyword) {
        return tourDataService.getTourDataListByKeyword(keyword);
    }

    @GetMapping("/accommodation/list")
    public ApiResponse<List<AccommodationResponseDto>> getAccommodationHome() {
        return accommodationService.getAccommodationHomeInfo();
    }

    @GetMapping("/accommodation")
    public ApiResponse<List<AccommodationResponseDto>> getAccommodationsByKeyword(@RequestParam String keyword) {
        return accommodationService.getAccommodationsByKeyword(keyword);
    }

}
