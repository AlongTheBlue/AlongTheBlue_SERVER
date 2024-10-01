package org.alongtheblue.alongtheblue_server.global.data.tourcommunity;

import lombok.Data;

import java.util.List;
@Data
public class ItemDTO {
    private List<String> tourImage;
    private String title;
    private String category;
    private String address;
    private String comment;
}
