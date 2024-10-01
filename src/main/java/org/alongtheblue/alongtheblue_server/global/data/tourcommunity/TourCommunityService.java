package org.alongtheblue.alongtheblue_server.global.data.tourcommunity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.alongtheblue.alongtheblue_server.domain.item.domain.Item;
import org.alongtheblue.alongtheblue_server.global.data.accommodation.Accommodation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TourCommunityService {

    private final UserTourCourseRepository userTourCourseRepository;
    private final TourPostItemRepository tourPostItemRepository;
    private final TourImageRepository tourImageRepository;
    private final TourPostHashTagRepository tourPostHashTagRepository;

    public UserTourCourse createPost(TourCourseRequestDto dto, List<MultipartFile> images) {
//    public UserTourCourse createPost(UserTourCourse userTourCourse, List<MultipartFile> images, List<List<Integer>> index) {
        // 먼저 userTourCourse 저장

        UserTourCourse userTourCourse= new UserTourCourse();
        userTourCourse.setTitle(dto.title());
        userTourCourse.setTourPostItems(dto.tourItems());
        userTourCourse.setTourPostHashTags(dto.hashTags());

        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        userTourCourse.setCreatedate(new Date());
        userTourCourse.setWriting(dto.writing());
        userTourCourse = userTourCourseRepository.save(userTourCourse);

        // tags와 items가 null인 경우 빈 리스트로 초기화
        List<TourPostHashTag> tags = userTourCourse.getTourPostHashTags() != null ? userTourCourse.getTourPostHashTags() : new ArrayList<>();
        List<TourPostItem> items = userTourCourse.getTourPostItems() != null ? userTourCourse.getTourPostItems() : new ArrayList<>();

        // TourData와 이미지 파일 처리 (순서대로 처리)
        for (int i = 0; i < dto.index().size(); i++) {
            for (int j = 0; j < dto.index().get(i).size(); j++) {
                TourPostItem data = items.get(i);
                MultipartFile image = images.get(dto.index().get(i).get(j));  // 인덱스로 매칭
                String imageUrl = saveImage(image);  // 이미지 저장 로직 호출
                TourImage tourImage= new TourImage();
                tourImage.setUrl(imageUrl);
                tourImage.setTourPostItem(data);
                tourImageRepository.save(tourImage);
            }
        }
        // items 리스트가 비어있지 않은 경우 처리
        if (!items.isEmpty()) {
            for (TourPostItem item : items) {
                // TourPostItem에 UserTourCourse 설정
                item.setUserTourCourse(userTourCourse);
            }
            tourPostItemRepository.saveAll(items);
        }

        // tags 리스트가 비어있지 않은 경우 처리
        if (!tags.isEmpty()) {
            for (TourPostHashTag hashtag : tags) {
                // TourPostItem에 UserTourCourse 설정
                hashtag.setTourCourseForHashTag(userTourCourse);
            }
            tourPostHashTagRepository.saveAll(tags);
        }

        // userTourCourse 반환 (이미 저장되었으므로)
        return userTourCourse;
    }

    // 이미지 파일 저장 로직
    private String saveImage(MultipartFile image) {
        // 실제로 파일을 서버에 저장하는 로직을 추가
        String filePath = "/path/to/images/" + image.getOriginalFilename();
        // 예시: File file = new File(filePath); image.transferTo(file);
        return filePath;
    }

    public List<TourDTO> allPost() {
        List<TourDTO> dtos= new ArrayList<>();
        List<UserTourCourse> tours= userTourCourseRepository.findAll();
        for(UserTourCourse tour: tours){
            TourDTO dto= new TourDTO();
            dto.setTitle(tour.getTitle());
            dto.setContentid(tour.getContentId());
            dto.setWriting(tour.getWriting());
            dto.setTags(tourPostHashTagRepository.findBytourCourseForHashTag(tour));
            List<TourPostItem> items= tourPostItemRepository.findByuserTourCourse(tour);
            dto.setImg(tourImageRepository.findBytourPostItem(items.get(0)).get(0).getUrl());
            dtos.add(dto);
        }

        return dtos;
    }

    public TourDTO onepost(Long postid) {
        Optional<UserTourCourse> temp= userTourCourseRepository.findById(postid);
        UserTourCourse course;
        TourDTO tourDTO= new TourDTO();
        ItemDTO itemDTO= new ItemDTO();
        List<ItemDTO> items= new ArrayList<>();
        if(temp.isPresent()) {
            course= temp.get();
            tourDTO.setTitle(course.getTitle());
            tourDTO.setWriting(course.getWriting());
            tourDTO.setTags(tourPostHashTagRepository.findBytourCourseForHashTag(course));
            tourDTO.setContentid(course.getContentId());
            for(TourPostItem item: tourPostItemRepository.findByuserTourCourse(course)){
                itemDTO.setName(item.getName());
                itemDTO.setCategory(item.getCategory());
                itemDTO.setAddress(item.getAddress());
                itemDTO.setComment(itemDTO.getComment());
                List<String> imgs= new ArrayList<>();
                for(TourImage img: tourImageRepository.findBytourPostItem(item)) imgs.add(img.getUrl());
                itemDTO.setTourImage(imgs);
                items.add(itemDTO);
            }
            tourDTO.setItems(items);
            return tourDTO;
        }
        else return null;
    }




}
