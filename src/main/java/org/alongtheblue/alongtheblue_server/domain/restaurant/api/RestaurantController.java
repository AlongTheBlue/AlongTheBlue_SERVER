package org.alongtheblue.alongtheblue_server.domain.restaurant.api;

import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.domain.restaurant.dto.response.PartRestaurantResponseDto;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.alongtheblue.alongtheblue_server.domain.global.CustomPage;
import org.alongtheblue.alongtheblue_server.domain.global.SimpleInformation;
import org.alongtheblue.alongtheblue_server.domain.global.dto.response.DetailResponseDto;
import org.alongtheblue.alongtheblue_server.domain.global.dto.response.HomeResponseDto;
import org.alongtheblue.alongtheblue_server.domain.restaurant.application.RestaurantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;
    // 데이터를 가져와서 데이터베이스에 저장하는 엔드포인트
    @GetMapping("/fetch")
    public void fetchAndSaveRestaurants() {
        restaurantService.fetchAndSaveData();
}

    // 1단계 저장 100개만
    @GetMapping("/save")
    public void saveRestaurants() {
        restaurantService.saveRestaurants();
    }
    // 2단계 저장
    @GetMapping("/save/introduction")
    public void saveRestaurantsIntroduction() {
        restaurantService.processSaveIntroduction();
    }
    // 3단계 저장
    @GetMapping("/save/info")
    public void saveRestaurantsInfo() {
        restaurantService.processSaveInfo();
    }
    // 4단계 저장
    @GetMapping("/save/image")
    public void saveRestaurantsImages() {
        restaurantService.processSaveImage();
    }

    // TODO 페이지네이션 구현 필요
    @GetMapping("/detail/all")
    public ApiResponse<CustomPage<SimpleInformation>> retrieveAll(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size) {
        return restaurantService.retrieveAll(page, size);
    }
//    public ApiResponse<List<RestaurantResponseDto>> getAll(){
//        return restaurantService.getAll();
//
//    }

    @GetMapping("/home")
    public ApiResponse<List<PartRestaurantResponseDto>> homeRestaurant(){
        return restaurantService.homerestaurant();
    }

    @GetMapping("/detail/{id}")
    public ApiResponse<DetailResponseDto> getRestaurant(@PathVariable String id){
        return restaurantService.getRestaurantDetail(id);
//        return restaurantService.getRestaurant(id);
    }

    @GetMapping("/home/list")
    public ApiResponse<List<HomeResponseDto>> getHomeRestaurant(){
        return restaurantService.getHomeRestaurant();
    }

    @GetMapping("/hashtags/{id}")
    public ApiResponse<List<String>> getHashtagsById(@PathVariable String id) {
        return restaurantService.getHashtagsById(id);
    }
}
