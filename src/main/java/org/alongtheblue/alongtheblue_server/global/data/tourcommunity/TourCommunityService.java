package org.alongtheblue.alongtheblue_server.global.data.tourcommunity;

import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.domain.userInfo.application.UserInfoService;
import org.alongtheblue.alongtheblue_server.domain.userInfo.domain.UserInfo;
import org.alongtheblue.alongtheblue_server.global.adapter.S3Adapter;
import org.alongtheblue.alongtheblue_server.global.common.response.ApiResponse;
import org.alongtheblue.alongtheblue_server.global.data.alongBlues.dto.request.CreateBlueItemRequestDto;
import org.alongtheblue.alongtheblue_server.global.data.tourcommunity.dto.request.CreateUserTourCourseServiceRequestDto;
import org.alongtheblue.alongtheblue_server.global.data.tourcommunity.dto.request.TourPostItemRequestDto;
import org.alongtheblue.alongtheblue_server.global.data.tourcommunity.dto.response.TourImageResponseDto;
import org.alongtheblue.alongtheblue_server.global.data.tourcommunity.dto.response.TourPostItemResponseDto;
import org.alongtheblue.alongtheblue_server.global.data.tourcommunity.dto.response.UserTourCourseDetailDto;
import org.alongtheblue.alongtheblue_server.global.error.ErrorCode;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class TourCommunityService {

    private final UserTourCourseRepository userTourCourseRepository;
    private final TourPostItemRepository tourPostItemRepository;
    private final TourImageRepository tourImageRepository;
    private final S3Adapter s3Adapter;
    private final ConversionService conversionService;
    private final UserInfoService userInfoService;

//    private final TourPostHashTagRepository tourPostHashTagRepository;

    // 여행따라 저장
    public ApiResponse<UserTourCourse> createPost(String uid, CreateUserTourCourseServiceRequestDto dto, List<MultipartFile> images) throws IOException {
        // 1. userInfo 가져오기
        UserInfo userInfo = userInfoService.retrieveUserInfo(uid).getData();

        // 2. 날짜 받아오기
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));

        // 3. tourPostItem 사진 리스트로 묶기
        List<TourPostItemRequestDto> tourPostItems = dto.tourPostItems();
        List<TourPostItem> tourItems = new ArrayList<>();

        for(TourPostItemRequestDto itemDto : tourPostItems) {

//        List<TourImage> tourImages = new ArrayList<>();
            if (dto.imgIndexArr() != null) {
                for (List<Integer> list : dto.imgIndexArr()) {
                    List<TourImage> tourImages = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        MultipartFile image = images.get(i);
                        ApiResponse<String> updateFileResponse = s3Adapter.uploadFile(image);
                        if (updateFileResponse.getStatus().is5xxServerError()) {
                            return ApiResponse.withError(ErrorCode.ERROR_S3_UPDATE_OBJECT);
                        }
                        String fileName = image.getOriginalFilename();
                        TourImage tourImage = TourImage.builder()
                                .fileName(fileName)
                                .url(updateFileResponse.getData())
                                .build();
                        tourImages.add(tourImage);
                    }
                    tourItems.add(itemDto.toEntity(tourImages));
                }
            }
        }
//        if (dto.imgIndexArr() != null) {
//            for (int i = 0; i < dto.imgIndexArr().size(); i++) {
//                for (int j = 0; j < dto.imgIndexArr().get(i).size(); j++) {
////                    TourPostItem tourItem = tourItems.get(i);
////                    System.out.println("i: " + i + " j: " + j);
////                    System.out.println(images.size());
//                    MultipartFile image = images.get(dto.imgIndexArr().get(i).get(j));  // 인덱스로 매칭
////                    String imageUrl = saveImageToS3(image);  // 이미지 저장 로직 호출
//                    ApiResponse<String> imageUrl = s3Adapter.uploadImage(image);
//                    TourImage tourImage = new TourImage();
//                    tourImage.setUrl(imageUrl.getData());
//                    tourImage.setTourPostItem(tourItem);
//                    tourImageRepository.save(tourImage);
//
//                }
//            }
//        }
        // 4. tourPostItem 만들기

        // 5. userTourCourse 만들기
        UserTourCourse userTourCourse = dto.toEntity(userInfo, new Date(), tourItems);

        // 6. userTourCourse 저장
        UserTourCourse savedUserTourCourse = userTourCourseRepository.save(userTourCourse);


//        List<TourPostItemRequestDto> tourPostItems = dto.tourPostItems();
//        List<TourPostItem> tourItems = new ArrayList<>();
//
//        for(TourPostItemRequestDto itemDto : tourPostItems) {
//            tourItems.add(itemDto.toEntity());
//        }



//        먼저 userTourCourse 저장
//        UserTourCourse userCourse = new UserTourCourse();
//        userCourse.setTitle(dto.title());
//        userCourse.setWriting(dto.content());
//        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
//        userCourse.setCreatedate(new Date());
//        userCourse.setWriting(dto.content());
//        userCourse = userTourCourseRepository.save(userCourse);
//
//        System.out.println(dto);

//        List<TourPostItem> tourItems = new ArrayList<>();
//        List<TourPostItemRequestDto> tourItemDtoList = dto.tourPostItems();
//        for (TourPostItemRequestDto tourItemDto : tourItemDtoList) {
//            TourPostItem tourItem = new TourPostItem();
//            tourItem.setName(tourItemDto.title());
//            tourItem.setComment(tourItemDto.comment());
//            tourItem.setAddress(tourItemDto.address());
//            tourItem.setXMap(tourItemDto.xMap());
//            tourItem.setYMap(tourItemDto.yMap());
//            tourItem.setCategory(tourItemDto.category());
//            tourItem.setUserTourCourse(userCourse);
////            tourItem.setContentId
//            tourPostItemRepository.save(tourItem);
//            tourItems.add(tourItem);
//        }
//        userCourse.setTourPostItems(tourItems);
//        userTourCourse.setTourPostHashTags(dto.hashTags());

        // tags와 items가 null인 경우 빈 리스트로 초기화
//        List<TourPostHashTag> tags = userTourCourse.getTourPostHashTags() != null ? userTourCourse.getTourPostHashTags() : new ArrayList<>();
//        List<TourPostItem> items = userCourse.getTourPostItems() != null ? userCourse.getTourPostItems() : new ArrayList<>();

        // 이미지 저장 - 예외처리
//        if (dto.imgIndexArr() != null) {
//            // 해당 TourPostItem에 이미지 저장 (순서대로 처리)
//            for (int i = 0; i < dto.imgIndexArr().size(); i++) {
//                for (int j = 0; j < dto.imgIndexArr().get(i).size(); j++) {
//                    TourPostItem tourItem = tourItems.get(i);
//                    System.out.println("i: " + i + " j: " + j);
//                    System.out.println(images.size());
//                    MultipartFile image = images.get(dto.imgIndexArr().get(i).get(j));  // 인덱스로 매칭
////                    String imageUrl = saveImageToS3(image);  // 이미지 저장 로직 호출
//                    ApiResponse<String> imageUrl = s3Adapter.uploadImage(image);
//                    TourImage tourImage = new TourImage();
//                    tourImage.setUrl(imageUrl.getData());
//                    tourImage.setTourPostItem(tourItem);
//                    tourImageRepository.save(tourImage);
//
//                }
//            }
//        }
//
//        dto.toEntity(userInfo, new Date(), tourItems);

        // items 리스트가 비어있지 않은 경우 처리
//        if (!items.isEmpty()) {
//            for (TourPostItem item : items) {
        // TourPostItem에 UserTourCourse 설정
//                item.setUserTourCourse(userCourse);
//            }
//            tourPostItemRepository.saveAll(items);
//        }

        // tags 리스트가 비어있지 않은 경우 처리
//        if (!tags.isEmpty()) {
//            for (TourPostHashTag hashtag : tags) {
//                // TourPostItem에 UserTourCourse 설정
//                hashtag.setTourCourseForHashTag(userTourCourse);
//            }
//            tourPostHashTagRepository.saveAll(tags);
//        }

        // userTourCourse 반환 (이미 저장되었으므로)

        return ApiResponse.ok("여행코스를 성공적으로 등록했습니다.", savedUserTourCourse);
    }

    // 이미지 파일 저장 로직
    private String saveImageToS3(MultipartFile image) {
        // 실제로 파일을 서버에 저장하는 로직을 추가
        String filePath = "/path/to/images/" + image.getOriginalFilename();
        // 예시: File file = new File(filePath); image.transferTo(file);
        return filePath;
    }

    // 여행따라 전체 조회 - 페이지네이션 필요
    public List<UserTourCourseDTO> getAllUserTourCourses() {
        List<UserTourCourse> userCourses = userTourCourseRepository.findAll();
        List<UserTourCourseDTO> userCourseDtoList = new ArrayList<>();
        for (UserTourCourse userCourse : userCourses) {
            UserTourCourseDTO userCourseDto = new UserTourCourseDTO();
            userCourseDto.setTitle(userCourse.getTitle());
            userCourseDto.setWriting(userCourse.getWriting());
//            dto.setTags(tourPostHashTagRepository.findBytourCourseForHashTag(tour));

            List<TourPostItem> items = tourPostItemRepository.findByuserTourCourse(userCourse);
            List<TourImage> images = tourImageRepository.findBytourPostItem(items.get(0));
            if (!images.isEmpty()) userCourseDto.setImgUrl(images.get(0).getUrl());
//            userCourseDto.setImgUrl(tourImageRepository.findBytourPostItem(items.get(0)).get(0).getUrl());
            userCourseDtoList.add(userCourseDto);
        }
        return userCourseDtoList;
    }

    public UserTourCourse findById(Long id) {
        Optional<UserTourCourse> userCourse = userTourCourseRepository.findById(id);
        return userCourse.orElse(null); // 예외처리 필요
    }

    // 특정 여행따라 조회
    public ApiResponse<UserTourCourseDetailDto> getUserCourseByID(Long id) {
        UserTourCourse userCourse = findById(id);

        List<TourPostItem> tourPostItems = userCourse.getTourPostItems();
        List<TourPostItemResponseDto> tourPostItemResponseDtoList = new ArrayList<>();
        for(TourPostItem tourPostItem : tourPostItems) {
            List<TourImageResponseDto> tourImageResponseDtoList = new ArrayList<>();
            for(TourImage tourImage : tourImageRepository.findBytourPostItem(tourPostItem)) {
                TourImageResponseDto tourImageResponseDto = new TourImageResponseDto(
                        tourImage.getId(),
                        tourImage.getUrl()
                );
                tourImageResponseDtoList.add(tourImageResponseDto);
            }

            TourPostItemResponseDto tourPostItemResponseDto = new TourPostItemResponseDto(
                    tourPostItem.getName(),
                    tourPostItem.getAddress(),
                    tourPostItem.getXMap(),
                    tourPostItem.getYMap(),
                    tourPostItem.getComment(),
                    tourPostItem.getCategory(),
                    tourPostItem.getContentId(),
                    tourImageResponseDtoList
            );
            tourPostItemResponseDtoList.add(tourPostItemResponseDto);
        }

        UserTourCourseDetailDto userTourCourseDetailDto = new UserTourCourseDetailDto(
                userCourse.getTitle(),
                userCourse.getWriting(),
                tourPostItemResponseDtoList
        );

        return ApiResponse.ok(userTourCourseDetailDto);


//        UserTourCourseDTO userCourseDto = new UserTourCourseDTO();
//        TourPostItemDTO tourPostItemDto = new TourPostItemDTO();
//        List<TourPostItemDTO> postItems = new ArrayList<>();
//        userCourseDto.setTitle(userCourse.getTitle());
//        userCourseDto.setWriting(userCourse.getWriting());
////            tourDTO.setTags(tourPostHashTagRepository.findBytourCourseForHashTag(course));
//
//        for (TourPostItem postItem : tourPostItemRepository.findByuserTourCourse(userCourse)) {
//            tourPostItemDto.setTitle(postItem.getName());
//            tourPostItemDto.setCategory(postItem.getCategory());
//            tourPostItemDto.setAddress(postItem.getAddress());
//            tourPostItemDto.setComment(tourPostItemDto.getComment());
//
//            List<String> imgUrls = new ArrayList<>();
//            for (TourImage img : tourImageRepository.findBytourPostItem(postItem))
//                imgUrls.add(img.getUrl());
//            tourPostItemDto.setTourImage(imgUrls);
//            postItems.add(tourPostItemDto);
//        }
//
//        userCourseDto.setPostItems(postItems);
//        return userCourseDto;
    }
}