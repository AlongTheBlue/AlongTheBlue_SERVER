package org.alongtheblue.alongtheblue_server.global.data.search;

import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.alongtheblue.alongtheblue_server.global.data.alongBlues.BlueRepository;
import org.alongtheblue.alongtheblue_server.global.data.alongBlues.BlueService;
import org.alongtheblue.alongtheblue_server.global.data.blue.Blue;
import org.alongtheblue.alongtheblue_server.global.data.cafe.Cafe;
import org.alongtheblue.alongtheblue_server.global.data.cafe.CafeRepository;
import org.alongtheblue.alongtheblue_server.global.data.cafe.CafeService;
import org.alongtheblue.alongtheblue_server.global.data.restaurant.Restaurant;
import org.alongtheblue.alongtheblue_server.global.data.restaurant.RestaurantRepository;
import org.alongtheblue.alongtheblue_server.global.data.restaurant.RestaurantService;
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

    private final BlueService blueService;
    private final CafeService cafeService;
    private final RestaurantService restaurantService;
    private final CafeRepository cafeRepository;

    public ApiResponse<List<AllCategoryResponseDto>> searchAllCategoryByKeyword(String keyword){
        List<AllCategoryResponseDto> allCategoryResponseDtos = new ArrayList<>();
        List<Blue> blues = blueRepository.findByNameContaining(keyword);
        List<Restaurant> restaurants = restaurantRepository.findByTitleContaining(keyword);

        for(Blue blue: blues) {
            AllCategoryResponseDto allCategoryResponseDto = getAllCategoryResponseDto(blue);
            allCategoryResponseDtos.add(allCategoryResponseDto);
        }
        for(Restaurant restaurant: restaurants) {
            AllCategoryResponseDto allCategoryResponseDto = getAllCategoryResponseDto(restaurant);
            allCategoryResponseDtos.add(allCategoryResponseDto);
        }
        return ApiResponse.ok("모든 카테고리 정보 조회를 성공하였습니다.", allCategoryResponseDtos);
    }

    public ApiResponse<List<AllCategoryResponseDto>> searchAllCategory() {
        List<AllCategoryResponseDto> allCategoryResponseDtos = new ArrayList<>();
        List<Blue> blues = blueRepository.findAll();
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<Cafe> cafes = cafeRepository.findAll();

        for(Blue blue: blues) {
            AllCategoryResponseDto allCategoryResponseDto = getAllCategoryResponseDto(blue);
            allCategoryResponseDtos.add(allCategoryResponseDto);
        }
        for(Restaurant restaurant: restaurants) {
            AllCategoryResponseDto allCategoryResponseDto = getAllCategoryResponseDto(restaurant);
            allCategoryResponseDtos.add(allCategoryResponseDto);
        }
        for(Cafe cafe: cafes) {
            AllCategoryResponseDto allCategoryResponseDto = getAllCategoryResponseDto(cafe);
            allCategoryResponseDtos.add(allCategoryResponseDto);
        }
        return  ApiResponse.ok("모든 카테고리 조회를 성공하였습니다.", allCategoryResponseDtos);
    }

    private AllCategoryResponseDto getAllCategoryResponseDto(Restaurant restaurant) {
        return new AllCategoryResponseDto(
                restaurant.getContentId(),
                restaurant.getTitle(),
                restaurant.getAddr(),
                restaurant.getXMap(),
                restaurant.getYMap()
        );
    }

    private AllCategoryResponseDto getAllCategoryResponseDto(Cafe cafe) {
        return new AllCategoryResponseDto(
                cafe.getContentId(),
                cafe.getTitle(),
                cafe.getAddr(),
                cafe.getXMap(),
                cafe.getYMap()
        );
    }

    private AllCategoryResponseDto getAllCategoryResponseDto(Blue blue) {
        return new AllCategoryResponseDto(
                Long.toString(blue.getId()),
                blue.getName(),
                blue.getAddress(),
                blue.getXMap(),
                blue.getYMap()
        );
    }
}
