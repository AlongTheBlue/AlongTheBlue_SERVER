package org.alongtheblue.alongtheblue_server.global.data.search;

import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.alongtheblue.alongtheblue_server.global.data.blue.BlueResponseDto;
import org.alongtheblue.alongtheblue_server.global.data.alongBlues.BlueService;
import org.alongtheblue.alongtheblue_server.global.data.cafe.CafeService;
import org.alongtheblue.alongtheblue_server.global.data.cafe.dto.PartCafeResponseDto;
import org.alongtheblue.alongtheblue_server.global.data.restaurant.RestaurantService;
import org.alongtheblue.alongtheblue_server.global.data.restaurant.dto.response.PartRestaurantResponseDto;
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
    public ApiResponse<List<AllCategoryResponseDto>> searchAllCategoryByKeyword(@RequestParam String keyword){
        return searchService.searchAllCategoryByKeyword(keyword);
    }

    @GetMapping("/all/list")
    public ApiResponse<List<AllCategoryResponseDto>> getAllCategory(){
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
}
