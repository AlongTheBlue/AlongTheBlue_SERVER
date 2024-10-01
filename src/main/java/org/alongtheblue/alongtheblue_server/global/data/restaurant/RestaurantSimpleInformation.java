package org.alongtheblue.alongtheblue_server.global.data.restaurant;

import java.util.List;

public interface RestaurantSimpleInformation {
    String getContentId();
    String getTitle();
    String getAddress();
    List<RestaurantImage> getImages();
}
