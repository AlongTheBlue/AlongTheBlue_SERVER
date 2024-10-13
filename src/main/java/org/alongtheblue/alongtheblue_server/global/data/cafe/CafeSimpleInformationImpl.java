package org.alongtheblue.alongtheblue_server.global.data.cafe;

import org.alongtheblue.alongtheblue_server.global.data.restaurant.RestaurantImage;
import org.alongtheblue.alongtheblue_server.global.data.restaurant.RestaurantSimpleInformation;

import java.util.List;

public class CafeSimpleInformationImpl implements CafeSimpleInformation {

    private String contentId;
    private String title;
    private String address;
    private List<CafeImage> images;

    public CafeSimpleInformationImpl(String contentId, String title, String address, List<CafeImage> images) {
        this.contentId = contentId;
        this.title = title;
        this.address = address;
        this.images = images;
    }

    @Override
    public String getContentId() {
        return contentId;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public List<CafeImage> getImages() {
        return images;
    }

    public void addImages(List<CafeImage> newImages) {
        this.images.addAll(newImages);  // 기존 이미지 리스트에 새로운 이미지 추가
    }
}