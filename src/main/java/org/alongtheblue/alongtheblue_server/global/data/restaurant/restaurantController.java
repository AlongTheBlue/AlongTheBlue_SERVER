package org.alongtheblue.alongtheblue_server.global.data.restaurant;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurant")
public class restaurantController {
    private final restaurantService restaurantService;
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

    @GetMapping("/detail/all")
    public List<RestaurantDTO> getRestaurant(){
        return restaurantService.getRestaurant();

    }

    @GetMapping("/home")
    public List<RestaurantDTO> homeRestaurant(){
        return restaurantService.homerestaurant();
    }

}
