package org.alongtheblue.alongtheblue_server.global.data.restaurant;

import java.util.List;

public class RestaurantSimpleInformationImpl implements RestaurantSimpleInformation {

    private String contentId;
    private String title;
    private String address;
    private List<RestaurantImage> images;

    public RestaurantSimpleInformationImpl(String contentId, String title, String address, List<RestaurantImage> images) {
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
    public List<RestaurantImage> getImages() {
        return images;
    }

    public void addImages(List<RestaurantImage> newImages) {
        this.images.addAll(newImages);  // 기존 이미지 리스트에 새로운 이미지 추가
    }
}