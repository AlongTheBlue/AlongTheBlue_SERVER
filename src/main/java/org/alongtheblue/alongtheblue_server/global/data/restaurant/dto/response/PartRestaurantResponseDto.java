package org.alongtheblue.alongtheblue_server.global.data.restaurant.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public record PartRestaurantResponseDto(
        String address,
        String title,
        String contentid,
        String img,
        String xMap,
        String yMap,
        String category
) {
//    private String address;
//    private String title;
//    private String contentid;
//    private String img;


//    private List<String> imgUrls;
//    private String introduction;
//    private String restDate;
//    private String infoCenter;
}
