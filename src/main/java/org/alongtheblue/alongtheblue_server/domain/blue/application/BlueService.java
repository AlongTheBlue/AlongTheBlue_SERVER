package org.alongtheblue.alongtheblue_server.domain.blue.application;

import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.domain.blue.dao.BlueRepository;
import org.alongtheblue.alongtheblue_server.domain.blue.dto.BlueResponseDto;
import org.alongtheblue.alongtheblue_server.domain.blue.dto.RecommendBlueDto;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.alongtheblue.alongtheblue_server.domain.blue.domain.Blue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlueService {

    private final BlueRepository blueRepository;

    public ApiResponse<List<BlueResponseDto>> getBlues() {
        System.out.println("blues 리스트");
        List<BlueResponseDto> blueResponseDtoList = new ArrayList<>();
        for(Blue blue: blueRepository.findAll()) {
            BlueResponseDto blueResponseDto = new BlueResponseDto(
                    blue.getId(),
                    blue.getName(),
                    blue.getXMap(),
                    blue.getYMap(),
                    blue.getAddress(),
                    blue.getCity(),
                    "tour"
            );
            blueResponseDtoList.add(blueResponseDto);
        }

        return ApiResponse.ok("해변 정보를 성공적으로 조회했습니다.", blueResponseDtoList);
    }

    public ApiResponse<BlueResponseDto> getBlueById(Long id) {
        Optional<Blue> optionalBlue = blueRepository.findById(id);
        if(optionalBlue.isPresent()) {
            Blue blue = optionalBlue.get();
            BlueResponseDto blueResponseDto = new BlueResponseDto(
                    blue.getId(),
                    blue.getName(),
                    blue.getXMap(),
                    blue.getYMap(),
                    blue.getAddress(),
                    blue.getCity(),
                    "tour"
            );
            return ApiResponse.ok("해변 정보를 성공적으로 조회했습니다.", blueResponseDto);
        }
        else return ApiResponse.ok("해변 ID를 찾지 못했습니다.", null);

    }

    public ApiResponse<List<BlueResponseDto>> getBluesByKeyword(String keyword) {
        List<Blue> optionalBlues = blueRepository.findByNameContaining(keyword);
        List<BlueResponseDto> blueResponseDtoList = new ArrayList<>();
        for(Blue blue: optionalBlues) {
            BlueResponseDto blueResponseDto = new BlueResponseDto(
                    blue.getId(),
                    blue.getName(),
                    blue.getXMap(),
                    blue.getYMap(),
                    blue.getAddress(),
                    blue.getCity(),
                    "tour"
            );
            blueResponseDtoList.add(blueResponseDto);
        }
        return ApiResponse.ok("해변 정보를 성공적으로 검색했습니다.", blueResponseDtoList);
    }

    public ApiResponse<List<RecommendBlueDto>> getRecommendBlues() {
        long totalCount = blueRepository.count();
        Random random = new Random();
        List<RecommendBlueDto> recommendBlueDtoList = new ArrayList<>();

        // 2개의 이미지를 가진 레코드를 모을 때까지 반복
        while (recommendBlueDtoList.size() < 4) {
            int randomOffset = random.nextInt((int) totalCount - 4); // 총 레코드 수에서 2개를 제외한 범위 내에서 랜덤 시작점 선택
            Pageable pageable = PageRequest.of(randomOffset, 4); // 한 번에 2개의 레코드 가져오기
            Page<Blue> bluePage = blueRepository.findAll(pageable); // Page 객체로 받음

            // 이미지를 가진 레코드만 필터링하여 DTO로 변환
            List<RecommendBlueDto> filteredList = bluePage.getContent().stream()
//                    .filter(blue -> !blue.getBlueImages().isEmpty()) // 이미지를 가진 레코드만 필터링
                    .map(blue -> new RecommendBlueDto(
                            blue.getId(),
                            blue.getName(),
                            blue.getXMap(),
                            blue.getYMap(),
                            blue.getAddress(),
                            blue.getCity(),
                            "tourData",
                            "http://tong.visitkorea.or.kr/cms/resource/01/3344401_image2_1.jpg"
                    ))
                    .toList();

            recommendBlueDtoList.addAll(filteredList);
            recommendBlueDtoList = recommendBlueDtoList.stream().distinct().limit(4).collect(Collectors.toList());
        }
        return ApiResponse.ok("이미지를 포함한 추천 해변 정보를 성공적으로 조회했습니다.", recommendBlueDtoList);
    }
}
