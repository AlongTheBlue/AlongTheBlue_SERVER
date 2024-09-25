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
    public Mono<Void> fetchAndSaveRestaurants() {
        return restaurantService.fetchAndSaveData();
}

    @GetMapping("/home")
    public List<RestaurantDTO> getRestaurant(){
        return restaurantService.getRestaurant();
    }
}
