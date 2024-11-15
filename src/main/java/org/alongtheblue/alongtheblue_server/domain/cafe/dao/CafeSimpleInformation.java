package org.alongtheblue.alongtheblue_server.domain.cafe.dao;

import org.alongtheblue.alongtheblue_server.domain.cafe.domain.CafeImage;

import java.util.List;

public interface CafeSimpleInformation {
    String getContentId();
    String getTitle();
    String getAddress();
    List<CafeImage> getImages();
}
