package org.alongtheblue.alongtheblue_server.global.data.tourcommunity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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


    public UserTourCourse createPost(UserTourCourse userTourCourse, List<MultipartFile> images, List<List<Integer>> index) {
        // 먼저 userTourCourse 저장
        userTourCourse = userTourCourseRepository.save(userTourCourse);

        // tags와 items가 null인 경우 빈 리스트로 초기화
        List<TourPostHashTag> tags = userTourCourse.getTourPostHashTags() != null ? userTourCourse.getTourPostHashTags() : new ArrayList<>();
        List<TourPostItem> items = userTourCourse.getTourPostItems() != null ? userTourCourse.getTourPostItems() : new ArrayList<>();

        // TourData와 이미지 파일 처리 (순서대로 처리)
        for (int i = 0; i < index.size(); i++) {
            for (int j = 0; j < index.get(i).size(); j++) {
                TourPostItem data = items.get(i);
                MultipartFile image = images.get(index.get(i).get(j));  // 인덱스로 매칭
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
}
