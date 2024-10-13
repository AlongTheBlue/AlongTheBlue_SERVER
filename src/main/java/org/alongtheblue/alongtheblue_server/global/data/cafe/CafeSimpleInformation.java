package org.alongtheblue.alongtheblue_server.global.data.cafe;

import java.util.List;

public interface CafeSimpleInformation {
    String getContentId();
    String getTitle();
    String getAddress();
    List<CafeImage> getImages();
}
