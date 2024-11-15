package org.alongtheblue.alongtheblue_server.domain.tourCommunity.dto.request;

import org.alongtheblue.alongtheblue_server.domain.tourCommunity.domain.TourImage;
import org.alongtheblue.alongtheblue_server.domain.tourCommunity.domain.TourPostItem;

import java.util.List;

public record TourPostItemRequestDto(
        String title,
        String category,
        String address,
        String xMap,
        String yMap,
        String comment
//        String contentsId
) {
    public TourPostItem toEntity(List<TourImage> tourImages) {
        TourPostItem item =  TourPostItem.builder()
                .name(title)
                .category(category)
                .address(address)
                .xMap(xMap)
                .yMap(yMap)
                .comment(comment)
                .tourImage(tourImages)
                .build();

//        for (TourImage tourImage : tourImages) {
//            tourImage.setTourPostItem(item);
//        }
        return item;
    }
}