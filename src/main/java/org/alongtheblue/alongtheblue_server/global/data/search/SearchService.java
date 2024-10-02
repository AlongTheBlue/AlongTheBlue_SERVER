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
import org.springframework.stereotype.Service;

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

    public ApiResponse<List<SearchResponseDto>> searchAllCategoryByKeyword(String keyword){
        List<SearchResponseDto> searchResponseDtoList = new ArrayList<>();
        List<Blue> blues = blueRepository.findByNameContaining(keyword);
        List<Restaurant> restaurants = restaurantRepository.findByTitleContaining(keyword);
        List<Cafe> cafes = cafeRepository.findByTitleContaining(keyword);

        for(Blue blue: blues) {
            SearchResponseDto searchResponseDto = getSearchResponseDto(blue);
            searchResponseDtoList.add(searchResponseDto);
        }
        for(Restaurant restaurant: restaurants) {
            SearchResponseDto searchResponseDto = getSearchResponseDto(restaurant);
            searchResponseDtoList.add(searchResponseDto);
        }
        for(Cafe cafe: cafes) {
            SearchResponseDto searchResponseDto = getSearchResponseDto(cafe);
            searchResponseDtoList.add(searchResponseDto);
        }
        return ApiResponse.ok("모든 카테고리 정보 조회를 성공하였습니다.", searchResponseDtoList);
    }

    public ApiResponse<List<SearchResponseDto>> searchAllCategory() {
        List<SearchResponseDto> searchResponseDtoList = new ArrayList<>();
        List<Blue> blues = blueRepository.findAll();
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<Cafe> cafes = cafeRepository.findAll();

        for(Blue blue: blues) {
            SearchResponseDto searchResponseDto = getSearchResponseDto(blue);
            searchResponseDtoList.add(searchResponseDto);
        }
        for(Restaurant restaurant: restaurants) {
            SearchResponseDto searchResponseDto = getSearchResponseDto(restaurant);
            searchResponseDtoList.add(searchResponseDto);
        }
        for(Cafe cafe: cafes) {
            SearchResponseDto searchResponseDto = getSearchResponseDto(cafe);
            searchResponseDtoList.add(searchResponseDto);
        }
        return  ApiResponse.ok("모든 카테고리 조회를 성공하였습니다.", searchResponseDtoList);
    }

    private SearchResponseDto getSearchResponseDto(Restaurant restaurant) {
        return new SearchResponseDto(
                restaurant.getContentId(),
                restaurant.getTitle(),
                restaurant.getAddr(),
                restaurant.getXMap(),
                restaurant.getYMap(),
                "restaurant"
        );
    }

    private SearchResponseDto getSearchResponseDto(Cafe cafe) {
        return new SearchResponseDto(
                cafe.getContentId(),
                cafe.getTitle(),
                cafe.getAddr(),
                cafe.getXMap(),
                cafe.getYMap(),
                "cafe"
        );
    }

    private SearchResponseDto getSearchResponseDto(Blue blue) {
        return new SearchResponseDto(
                Long.toString(blue.getId()),
                blue.getName(),
                blue.getAddress(),
                blue.getXMap(),
                blue.getYMap(),
                "blue"
        );
    }
}
