package org.alongtheblue.alongtheblue_server.domain.global;

import java.util.List;

public interface SimpleInformation<T> {
    String getContentId();
    String getTitle();
    String getAddress();
    List<T> getImages();
}
