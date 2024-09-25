package org.alongtheblue.alongtheblue_server.global.data.tourcommunity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
