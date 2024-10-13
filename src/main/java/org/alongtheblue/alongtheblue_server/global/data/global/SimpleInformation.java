package org.alongtheblue.alongtheblue_server.global.data.global;

import org.alongtheblue.alongtheblue_server.global.data.restaurant.RestaurantImage;

import java.util.List;

public interface RestaurantSimpleInformation {
    String getContentId();
    String getTitle();
    String getAddress();
    List<RestaurantImage> getImages();
}
