package org.alongtheblue.alongtheblue_server.global.data.alongBlues;

import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.alongtheblue.alongtheblue_server.global.data.alongBlues.dto.request.CreateBlueCourseServiceRequestDto;
import org.alongtheblue.alongtheblue_server.global.data.alongBlues.dto.request.CreateBlueItemRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class BlueCourseService {

    private final BlueCourseRepository blueCourseRepository;

//    private final BlueItemRepository blueItemRepository;

//    public void createCourse(BlueCourse blueCourse) {
    public ApiResponse<BlueCourse> createCourse(CreateBlueCourseServiceRequestDto dto) {
        List<CreateBlueItemRequestDto> itemDtoList = dto.blueItems();

        List<BlueItem> itemList = new ArrayList<>();
        for(CreateBlueItemRequestDto itemDto : itemDtoList) {
            itemList.add(itemDto.toEntity());
        }
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        Date now = new Date();
        BlueCourse blueCourse = dto.toEntity(now, itemList);
        BlueCourse savedBlueCourse = blueCourseRepository.save(blueCourse);
        return ApiResponse.ok("코스를 성공적으로 등록했습니다.", savedBlueCourse);
//        blueItemRepository.saveAll(blueCourse.getBlueItems());
    }

    public List<BlueCourse> getAllCourse() {
        return blueCourseRepository.findAll();
    }

    public BlueCourse getCourse(Long id) {
        Optional<BlueCourse>course= blueCourseRepository.findById(id);
        return course.orElse(null);
    }


}
