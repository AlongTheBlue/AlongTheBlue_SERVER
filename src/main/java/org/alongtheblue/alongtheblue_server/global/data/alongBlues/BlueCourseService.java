package org.alongtheblue.alongtheblue_server.global.data.alongBlues;

import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.domain.userInfo.application.UserInfoService;
import org.alongtheblue.alongtheblue_server.domain.userInfo.domain.UserInfo;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.alongtheblue.alongtheblue_server.global.data.alongBlues.dto.request.CreateBlueCourseServiceRequestDto;
import org.alongtheblue.alongtheblue_server.global.data.alongBlues.dto.request.CreateBlueItemRequestDto;
import org.alongtheblue.alongtheblue_server.global.data.alongBlues.dto.response.BlueCourseResponseDto;
import org.alongtheblue.alongtheblue_server.global.data.alongBlues.dto.response.BlueItemResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class BlueCourseService {

    private final BlueCourseRepository blueCourseRepository;
    private final UserInfoService userInfoService;

//    private final BlueItemRepository blueItemRepository;

//    public void createCourse(BlueCourse blueCourse) {
    public ApiResponse<BlueCourse> createCourse(String uid, CreateBlueCourseServiceRequestDto dto) {
        UserInfo userInfo = userInfoService.retrieveUserInfo(uid).getData();

        List<CreateBlueItemRequestDto> itemDtoList = dto.blueItems();

        List<BlueItem> itemList = new ArrayList<>();
        for(CreateBlueItemRequestDto itemDto : itemDtoList) {
            itemList.add(itemDto.toEntity());
        }
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        Date now = new Date();
        BlueCourse blueCourse = dto.toEntity(now, itemList, userInfo);
        BlueCourse savedBlueCourse = blueCourseRepository.save(blueCourse);
        return ApiResponse.ok("코스를 성공적으로 등록했습니다.", savedBlueCourse);
//        blueItemRepository.saveAll(blueCourse.getBlueItems());
    }

    public List<BlueCourse> getAllCourse() {
        return blueCourseRepository.findAll();
    }

    public ApiResponse<BlueCourseResponseDto> getBlueCourse(Long id) {
        Optional<BlueCourse> course = blueCourseRepository.findById(id);
        if(course.isEmpty())
            return ApiResponse.ok("해당 ID의 코스를 찾지 못했습니다.");

        BlueCourse blueCourse = course.get();
        List<BlueItemResponseDto> blueItemResponseDtoList = new ArrayList<>();
        for(BlueItem blueItem :  blueCourse.getBlueItems()) {
            BlueItemResponseDto blueItemResponseDto = new BlueItemResponseDto(
                    blueItem.getName(),
                    blueItem.getAddress(),
                    blueItem.getXMap(),
                    blueItem.getYMap(),
                    blueItem.getCategory()
            );
            blueItemResponseDtoList.add(blueItemResponseDto);
        }
        BlueCourseResponseDto blueCourseResponseDto = new BlueCourseResponseDto(
                blueCourse.getTitle(),
                blueItemResponseDtoList
        );
        return ApiResponse.ok(blueCourseResponseDto);
    }
}
