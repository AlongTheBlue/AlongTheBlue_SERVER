package org.alongtheblue.alongtheblue_server.domain.search.domain;

import java.util.List;

public interface SearchInformation<T> {
    String getContentId();
    String getTitle();
    String getAddress();
    String getXMap();
    String getYMap();
    List<T> getImages();
}
