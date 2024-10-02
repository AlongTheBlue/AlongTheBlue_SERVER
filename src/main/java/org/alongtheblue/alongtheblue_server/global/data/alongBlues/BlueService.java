package org.alongtheblue.alongtheblue_server.global.data.alongBlues;

import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.alongtheblue.alongtheblue_server.global.data.blue.Blue;
import org.alongtheblue.alongtheblue_server.global.data.blue.BlueResponseDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return ApiResponse.ok("해변 정보를 성공적으로 조회했습니다.", blueResponseDtoList);
    }
}
