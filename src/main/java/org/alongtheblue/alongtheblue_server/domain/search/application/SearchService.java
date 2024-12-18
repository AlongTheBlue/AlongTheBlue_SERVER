package org.alongtheblue.alongtheblue_server.domain.search.application;

import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.domain.search.dto.SearchResponseDto;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.alongtheblue.alongtheblue_server.domain.accommodation.domain.Accommodation;
import org.alongtheblue.alongtheblue_server.domain.accommodation.dao.AccommodationRepository;
import org.alongtheblue.alongtheblue_server.domain.blue.dao.BlueRepository;
import org.alongtheblue.alongtheblue_server.domain.blue.domain.Blue;
import org.alongtheblue.alongtheblue_server.domain.cafe.domain.Cafe;
import org.alongtheblue.alongtheblue_server.domain.cafe.dao.CafeRepository;
import org.alongtheblue.alongtheblue_server.domain.restaurant.domain.Restaurant;
import org.alongtheblue.alongtheblue_server.domain.restaurant.dao.RestaurantRepository;
import org.alongtheblue.alongtheblue_server.domain.tourData.domain.TourData;
import org.alongtheblue.alongtheblue_server.domain.tourData.dao.TourDataRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final BlueRepository blueRepository;
    private final RestaurantRepository restaurantRepository;
    private final CafeRepository cafeRepository;
    private final TourDataRepository tourDataRepository;
    private final AccommodationRepository accommodationRepository;

//    public ApiResponse<List<SearchResponseDto>> searchAllCategoryByKeyword(String keyword){
//        List<SearchResponseDto> searchResponseDtoList = new ArrayList<>();
//        List<Blue> blues = blueRepository.findByNameContaining(keyword);
//        List<Restaurant> restaurants = restaurantRepository.findByTitleContaining(keyword);
//        List<Cafe> cafes = cafeRepository.findByTitleContaining(keyword);
//        List<TourData> tourDataList = tourDataRepository.findByTitleContaining(keyword);
//        List<Accommodation> accommodations = accommodationRepository.findByTitleContaining(keyword);
//
//        addSearchResponseDto(searchResponseDtoList, blues, restaurants, cafes, tourDataList, accommodations);
//        return ApiResponse.ok("모든 카테고리에서 검색을 성공하였습니다.", searchResponseDtoList);
//    }

    public ApiResponse<List<SearchResponseDto>> searchAllCategory() {
        List<SearchResponseDto> searchResponseDtoList = new ArrayList<>();
        List<Blue> blues = blueRepository.findAll();
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<Cafe> cafes = cafeRepository.findAll();
        List<TourData> tourDataList = tourDataRepository.findAll();
        List<Accommodation> accommodations = accommodationRepository.findAll();

        addSearchResponseDto(searchResponseDtoList, blues, restaurants, cafes, tourDataList, accommodations);
        return  ApiResponse.ok("모든 카테고리 조회를 성공하였습니다.", searchResponseDtoList);
    }

    private void addSearchResponseDto(List<SearchResponseDto> searchResponseDtoList, List<Blue> blues, List<Restaurant> restaurants, List<Cafe> cafes, List<TourData> tourDataList, List<Accommodation> accommodations) {
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
        for(TourData tourData: tourDataList) {
            SearchResponseDto searchResponseDto = getSearchResponseDto(tourData);
            searchResponseDtoList.add(searchResponseDto);
        }
        for(Accommodation accommodation: accommodations) {
            SearchResponseDto searchResponseDto = getSearchResponseDto(accommodation);
            searchResponseDtoList.add(searchResponseDto);
        }
    }

    private SearchResponseDto getSearchResponseDto(Restaurant restaurant) {
        return new SearchResponseDto(
                restaurant.getContentId(),
                restaurant.getTitle(),
                restaurant.getAddress(),
                restaurant.getXMap(),
                restaurant.getYMap(),
                "restaurant"
        );
    }

    private SearchResponseDto getSearchResponseDto(Cafe cafe) {
        return new SearchResponseDto(
                cafe.getContentId(),
                cafe.getTitle(),
                cafe.getAddress(),
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

    private SearchResponseDto getSearchResponseDto(TourData tourData) {
        return new SearchResponseDto(
                tourData.getContentId(),
                tourData.getTitle(),
                tourData.getAddress(),
                tourData.getXMap(),
                tourData.getYMap(),
                "tourData"
        );
    }

    private SearchResponseDto getSearchResponseDto(Accommodation accommodation) {
        return new SearchResponseDto(
                accommodation.getContentId(),
                accommodation.getTitle(),
                accommodation.getAddress(),
                accommodation.getXMap(),
                accommodation.getYMap(),
                "accommodation"
        );
    }
}
