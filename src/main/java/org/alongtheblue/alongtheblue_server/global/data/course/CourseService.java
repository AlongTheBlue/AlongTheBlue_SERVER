package org.alongtheblue.alongtheblue_server.global.data.course;

import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.alongtheblue.alongtheblue_server.global.data.courseHashTag.CourseHashTag;
import org.alongtheblue.alongtheblue_server.global.data.courseImage.CourseImage;
import org.alongtheblue.alongtheblue_server.global.data.courseImage.CourseImageDto;
import org.alongtheblue.alongtheblue_server.global.data.courseItem.CourseItem;
import org.alongtheblue.alongtheblue_server.global.data.courseItem.CourseItemRequestDto;
import org.alongtheblue.alongtheblue_server.global.data.courseItem.CourseItemResponseDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public ApiResponse<List<CourseHomeDto>> getHomeCourse() {
        List<Course> courses = courseRepository.findAll();
        List<CourseHomeDto> courseHomeDtoList = new ArrayList<>();
        int index = 0;
        while (courseHomeDtoList.size() < 2){
            Course course = courses.get(index++);
            CourseHomeDto courseHomeDto = new CourseHomeDto(
                    course.getId(),
                    course.getSubtitle(),
                    course.getTitle(),
                    course.getCourseItemList().get(0).getTourImage().get(0).getUrl(),
                    course.getCourseHashTags().get(0).getTitle()
            );
            courseHomeDtoList.add(courseHomeDto);
        }
        return ApiResponse.ok(courseHomeDtoList);
    }

    public ApiResponse<CourseDetailDto> getDetailCourse(Long id) {
        Course course = findById(id);

        List<CourseItem> courseItemList = course.getCourseItemList();
        List<CourseItemResponseDto> courseItemResponseDtoList = new ArrayList<>();
        for(CourseItem courseItem : courseItemList){
            List<CourseImage> courseImages = courseItem.getTourImage();
            List<CourseImageDto> courseImageDtoList = new ArrayList<>();
            for(CourseImage courseImage : courseImages){
                CourseImageDto courseImageDto = new CourseImageDto(
                        courseImage.getId(),
                        courseImage.getUrl()
                );
                courseImageDtoList.add(courseImageDto);
            }
            CourseItemResponseDto courseItemResponseDto = new CourseItemResponseDto(
                    courseItem.getTitle(),
                    courseItem.getAddress(),
                    courseItem.getContent(),
                    courseItem.getCategory(),
                    courseItem.getXMap(),
                    courseItem.getYMap(),
                    courseImageDtoList
            );
            courseItemResponseDtoList.add(courseItemResponseDto);
        }

        List<CourseHashTag> courseHashTagList = course.getCourseHashTags();

        CourseDetailDto courseDetailDto = new CourseDetailDto(
                course.getId(),
                course.getSubtitle(),
                course.getTitle(),
                course.getContent(),
                courseHashTagList,
                courseItemResponseDtoList
        );
        return ApiResponse.ok(courseDetailDto);
    }

    public ApiResponse<String> saveCourse(CourseRequestDto tourCourseRequestDto) {
        return null;
    }

    public Course findById(Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent())
            return optionalCourse.get();
        else
            throw new RuntimeException("해당 ID의 여행코스가 존재하지 않습니다.");
    }
}
