package org.alongtheblue.alongtheblue_server.domain.search.api;

import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.domain.search.domain.SearchInformation;
import org.alongtheblue.alongtheblue_server.domain.search.dto.SearchResponseDto;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.alongtheblue.alongtheblue_server.domain.accommodation.dto.AccommodationResponseDto;
import org.alongtheblue.alongtheblue_server.domain.accommodation.application.AccommodationService;
import org.alongtheblue.alongtheblue_server.domain.blue.application.BlueService;
import org.alongtheblue.alongtheblue_server.domain.blue.dto.BlueResponseDto;
import org.alongtheblue.alongtheblue_server.domain.cafe.application.CafeService;
import org.alongtheblue.alongtheblue_server.domain.cafe.dto.PartCafeResponseDto;
import org.alongtheblue.alongtheblue_server.domain.global.CustomPage;
import org.alongtheblue.alongtheblue_server.domain.restaurant.application.RestaurantService;
import org.alongtheblue.alongtheblue_server.domain.restaurant.dto.response.PartRestaurantResponseDto;
import org.alongtheblue.alongtheblue_server.domain.search.application.SearchService;
import org.alongtheblue.alongtheblue_server.domain.tourData.application.TourDataService;
import org.alongtheblue.alongtheblue_server.domain.tourData.dto.TourDataResponseDto;
import org.springframework.web.bind.annotation.*;

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
    public ApiResponse<CustomPage<SearchInformation>> searchRestaurantsByKeyword(@RequestParam String keyword,
                                                                                 @RequestParam(defaultValue = "0") int page,
                                                                                 @RequestParam(defaultValue = "10") int size) {
        return restaurantService.getRestaurantsByKeyword(keyword, page, size);
    }
    @GetMapping("/restaurant/{id}")
    public ApiResponse<SearchResponseDto> getRestaurantInfo(@PathVariable String id) {
        return restaurantService.getRestaurantInfo(id);
    }

//    @GetMapping("/all")
//    public ApiResponse<List<SearchResponseDto>> searchAllCategoryByKeyword(@RequestParam String keyword){
//        return searchService.searchAllCategoryByKeyword(keyword);
//    }

    @GetMapping("/all/list")
    public ApiResponse<List<SearchResponseDto>> getAllCategory() {
        return searchService.searchAllCategory();
    }

    @GetMapping("/cafe/list")
    public ApiResponse<List<PartCafeResponseDto>> getCafesHome() {
        return cafeService.getCafesHome();
    }

    @GetMapping("/cafe")
    public ApiResponse<CustomPage<SearchInformation>> searchCafesByKeyword(@RequestParam String keyword,
                                                                           @RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size) {
        return cafeService.getCafesByKeyword(keyword, page, size);
    }

    @GetMapping("/cafe/{id}")
    public ApiResponse<SearchResponseDto> getCafeInfo(@PathVariable String id) {
        return cafeService.getCafeInfo(id);
    }

    @GetMapping("/tourData/list")
    public ApiResponse<List<TourDataResponseDto>> getTourDataList() {
        return tourDataService.getTourDataListHome();
    }

    @GetMapping("/tourData")
    public ApiResponse<CustomPage<SearchInformation>> getTourDataListByKeyword(@RequestParam String keyword,
                                                                               @RequestParam(defaultValue = "0") int page,
                                                                               @RequestParam(defaultValue = "10") int size) {
        return tourDataService.getTourDataListByKeyword(keyword, page, size);
    }

    @GetMapping("/tourData/{id}")
    public ApiResponse<SearchResponseDto> getTourDataInfo(@PathVariable String id) {
        return tourDataService.getTourDataInfo(id);
    }

    @GetMapping("/accommodation/list")
    public ApiResponse<List<AccommodationResponseDto>> getAccommodationHome() {
        return accommodationService.getAccommodationHomeInfo();
    }

    @GetMapping("/accommodation")
    public ApiResponse<CustomPage<SearchInformation>> getAccommodationsByKeyword(@RequestParam String keyword,
                                                                                 @RequestParam(defaultValue = "0") int page,
                                                                                 @RequestParam(defaultValue = "10") int size) {
        return accommodationService.getAccommodationsByKeyword(keyword, page, size);
    }

    @GetMapping("/accommodation/{id}")
    public ApiResponse<SearchResponseDto> getAccommodationInfo(@PathVariable String id) {
        return accommodationService.getAccommodationInfo(id);
    }

}
