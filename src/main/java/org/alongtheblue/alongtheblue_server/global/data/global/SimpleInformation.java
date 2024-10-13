package org.alongtheblue.alongtheblue_server.global.data.global;

import org.alongtheblue.alongtheblue_server.global.data.restaurant.RestaurantImage;

import java.util.List;

public interface SimpleInformation<T> {
    String getContentId();
    String getTitle();
    String getAddress();
    List<T> getImages();
}
