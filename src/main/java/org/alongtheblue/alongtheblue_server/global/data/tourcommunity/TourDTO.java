package org.alongtheblue.alongtheblue_server.global.data.tourcommunity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class TourDTO {
    private String title;
    private String writing;
    private List<TourPostHashTag> tags;
    private String contentid;
    private String img;
    private List<ItemDTO> items;
}
