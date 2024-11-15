package org.alongtheblue.alongtheblue_server.domain.tourCommunity.dto;

import lombok.Data;

import java.util.List;
@Data
public class TourPostItemDTO {
    private List<String> tourImage;
    // private List<TourImage> images;
    private String title;
    private String category;
    private String address;
    private String comment;
//    private String contentid;
}
