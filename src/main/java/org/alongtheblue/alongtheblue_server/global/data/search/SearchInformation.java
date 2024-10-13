package org.alongtheblue.alongtheblue_server.global.data.search;

import java.util.List;

public interface SearchInformation<T> {
    String getContentId();
    String getTitle();
    String getAddress();
    String getXMap();
    String getYMap();
    List<T> getImages();
}
