package org.alongtheblue.alongtheblue_server.global.data.tourcommunity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.alongtheblue.alongtheblue_server.global.data.accommodation.Accommodation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class TourCommunityService {

    @Autowired
    private final UserTourCourseRepository userTourCourseRepository;
    @Autowired
    private final TourPostItemRepository tourPostItemRepository;
    @Autowired
    private final TourImageRepository tourImageRepository;
    @Autowired
    private final TourPostHashTagRepository tourPostHashTagRepository;

    public TourCommunityService(UserTourCourseRepository userTourCourseRepository, TourPostItemRepository tourPostItemRepository, TourImageRepository tourImageRepository, TourPostHashTagRepository tourPostHashTagRepository) {
        this.userTourCourseRepository = userTourCourseRepository;
        this.tourPostItemRepository = tourPostItemRepository;
        this.tourImageRepository = tourImageRepository;
        this.tourPostHashTagRepository = tourPostHashTagRepository;
    }


    public UserTourCourse createPost(UserTourCourse userTourCourse) {
        // 먼저 userTourCourse 저장
        userTourCourse = userTourCourseRepository.save(userTourCourse);

        // tags와 items가 null인 경우 빈 리스트로 초기화
        List<TourPostHashTag> tags = userTourCourse.getTourPostHashTags() != null ? userTourCourse.getTourPostHashTags() : new ArrayList<>();
        List<TourPostItem> items = userTourCourse.getTourPostItems() != null ? userTourCourse.getTourPostItems() : new ArrayList<>();

        // items 리스트가 비어있지 않은 경우 처리
        if (!items.isEmpty()) {
            for (TourPostItem item : items) {
                // TourPostItem에 UserTourCourse 설정
                item.setUserTourCourse(userTourCourse);

                // TourImage가 null일 수 있으므로 null 체크 추가
                List<TourImage> images = item.getTourImage() != null ? item.getTourImage() : new ArrayList<>();
                if (!images.isEmpty()) {
                    tourImageRepository.saveAll(images);
                }
            }
            tourPostItemRepository.saveAll(items);
        }

        // tags 리스트가 비어있지 않은 경우 처리
        if (!tags.isEmpty()) {
            tourPostHashTagRepository.saveAll(tags);
        }

        // userTourCourse 반환 (이미 저장되었으므로)
        return userTourCourse;
    }


    public List<UserTourCourse> allPost() {
        return userTourCourseRepository.findAll();
    }

    public UserTourCourse onepost(Long postid) {
        Optional<UserTourCourse> temp= userTourCourseRepository.findById(postid);
        UserTourCourse userTourCourse;
        if(temp.isPresent()) {
            userTourCourse= temp.get();
            return  userTourCourse;
        }
        else return null;

    }

    public void updateOriginImageUrls(String contentId) {
        String url = "https://apis.data.go.kr/B551011/KorService1/detailImage1?serviceKey=GY8BQwWZJD6QX3tfaQTpfYMRjcRnaHoPAxn/7u6ZffwScPHeO3TYZgA0zMPfnO/iSc/PunU/5rZYIa5jj98sUw==&MobileOS=ETC&MobileApp=AppTest&_type=json&contentId=" + contentId + "&imageYN=Y&subImageYN=Y&numOfRows=10&pageNo=1";

        WebClient webClient = WebClient.builder().build();

        try {
            System.out.println("Requesting URL: " + url);

            // API로부터 JSON 응답 가져오기
            String jsonResponse = webClient.get()
                    .uri(url)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), clientResponse -> {
                        return clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    System.out.println("Error Response: " + errorBody);
                                    return Mono.error(new RuntimeException("API 요청 실패"));
                                });
                    })
                    .bodyToMono(String.class)
                    .block();

            // JSON 응답 형식 확인
            if (!jsonResponse.startsWith("{") && !jsonResponse.startsWith("[")) {
                throw new RuntimeException("예상치 못한 응답 형식: " + jsonResponse);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode itemsNode = rootNode
                    .path("response")
                    .path("body")
                    .path("items")
                    .path("item");

            // contentId를 기반으로 UserTourCourse 찾기
            Optional<UserTourCourse> userTourCourseOpt = userTourCourseRepository.findByContentId(contentId);
            if (userTourCourseOpt.isEmpty()) {
                System.err.println("UserTourCourse을 찾을 수 없음: contentId=" + contentId);
                return; // UserTourCourse가 없으면 종료
            }

            UserTourCourse userTourCourse = userTourCourseOpt.get();

            // 관련된 TourPostItem 가져오기 또는 생성
            TourPostItem tourPostItem = new TourPostItem();
            tourPostItem.setUserTourCourse(userTourCourse);
            tourPostItem.setName("Generated Item");  // 필요에 따라 설정
            tourPostItem.setCategory("Category");   // 필요에 따라 설정
            tourPostItem.setAddress("Sample Address");  // 필요에 따라 설정
            tourPostItem = tourPostItemRepository.save(tourPostItem); // 저장

            // 여러 개의 originimgurl을 가져옴
            List<String> originImgUrls = new ArrayList<>();
            for (JsonNode itemNode : itemsNode) {
                String originimgurl = itemNode.path("originimgurl").asText();
                if (!originimgurl.isEmpty()) {
                    originImgUrls.add(originimgurl);
                    System.out.println("Image URL: " + originimgurl);

                    // TourImage 생성 및 저장
                    TourImage tourImage = new TourImage();
                    tourImage.setUrl(originimgurl);
                    tourImage.setTourPostItem(tourPostItem); // TourPostItem과 연관 설정
                    tourImageRepository.save(tourImage);
                    System.out.println("TourImage 저장 완료: " + originimgurl);
                }
            }

        } catch (JsonProcessingException e) {
            System.err.println("JSON 파싱 오류: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
