package org.alongtheblue.alongtheblue_server.global.data.alongBlues;

import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.alongtheblue.alongtheblue_server.global.data.restaurant.dto.response.PartRestaurantResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlueService {
    @Autowired
    private BlueRepository blueRepository;

    public ApiResponse<List<BlueResponseDto>> getBlues() {
        List<BlueResponseDto> blueResponseDtoList = new ArrayList<>();
        for(Blue blue: blueRepository.findAll()) {
            BlueResponseDto blueResponseDto = new BlueResponseDto(
                    blue.getId(),
                    blue.getName(),
                    blue.getXMap(),
                    blue.getYMap(),
                    blue.getAddress(),
                    blue.getCity()
            );
            blueResponseDtoList.add(blueResponseDto);
        }

        return ApiResponse.ok("해변 정보를 성공적으로 조회했습니다.", blueResponseDtoList);
    }

}
