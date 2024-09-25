package org.alongtheblue.alongtheblue_server.global.data.tourcommunity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        List<TourPostHashTag> tags= userTourCourse.getTourPostHashTags();
        List<TourPostItem> items= userTourCourse.getTourPostItems();
        if(!items.isEmpty()) {
            for (TourPostItem item : items) {
                List<TourImage> images = item.getTourImage();
                tourImageRepository.saveAll(images);
            }
        }
        if(!tags.isEmpty()) tourPostHashTagRepository.saveAll(tags);

        if(!items.isEmpty()) tourPostItemRepository.saveAll(items);



        return userTourCourseRepository.save(userTourCourse);
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
