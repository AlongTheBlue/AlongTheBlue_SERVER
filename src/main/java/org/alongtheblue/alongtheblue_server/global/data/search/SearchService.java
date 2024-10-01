package org.alongtheblue.alongtheblue_server.global.data.search;

import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.alongtheblue.alongtheblue_server.global.data.alongBlues.BlueRepository;
import org.alongtheblue.alongtheblue_server.global.data.blue.Blue;
import org.alongtheblue.alongtheblue_server.global.data.restaurant.Restaurant;
import org.alongtheblue.alongtheblue_server.global.data.restaurant.RestaurantRepository;
import org.alongtheblue.alongtheblue_server.global.data.tourData.TourDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final RestaurantRepository restaurantRepository;
    private final BlueRepository blueRepository;

    public ApiResponse<List<AllCategoryResponseDto>> searchAllCategoryByKeyword(String keyword){
        List<Blue> blues = blueRepository.findByNameContaining(keyword);
        List<Restaurant> restaurants = restaurantRepository.findByTitleContaining(keyword);
        List<AllCategoryResponseDto> allCategoryResponseDtos = new ArrayList<>();
        if(!blues.isEmpty()) {
            for(Blue blue: blues) {
                AllCategoryResponseDto allCategoryResponseDto = new AllCategoryResponseDto(
                        Long.toString(blue.getId()),
                        blue.getName(),
                        blue.getAddress(),
                        blue.getXMap(),
                        blue.getYMap()
                );
                allCategoryResponseDtos.add(allCategoryResponseDto);
            }
        }
        if(!restaurants.isEmpty()) {
            for(Restaurant restaurant: restaurants) {
                AllCategoryResponseDto allCategoryResponseDto = new AllCategoryResponseDto(
                        restaurant.getContentId(),
                        restaurant.getTitle(),
                        restaurant.getAddr(),
                        restaurant.getXMap(),
                        restaurant.getYMap()
                );
                allCategoryResponseDtos.add(allCategoryResponseDto);
            }
        }
        return ApiResponse.ok("검색 정보 조회를 성공하였습니다.", allCategoryResponseDtos);
    }
}
