package org.alongtheblue.alongtheblue_server.global.data.cafe;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CafeDTO {
    private String address;
    private String title;
    private String contentid;
    private String img;
    private List<String> imgUrls;
    private String introduction;
    private String restDate;
    private String infoCenter;
}
