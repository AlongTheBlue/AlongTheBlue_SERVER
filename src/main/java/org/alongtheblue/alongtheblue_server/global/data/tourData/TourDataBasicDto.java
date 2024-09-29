package org.alongtheblue.alongtheblue_server.global.data.tourData;

import lombok.Data;

import java.util.List;

@Data
public class TourDataBasicDto {
    private String id;
    private String name;
    private String roadaddress;
    private String title;
    private List<String> originimgurl;  // 추가된 필드
    private String introduction;  // 추가된 필드
    private String checkintime;   // 추가된 필드
    private String infocenter;

}
