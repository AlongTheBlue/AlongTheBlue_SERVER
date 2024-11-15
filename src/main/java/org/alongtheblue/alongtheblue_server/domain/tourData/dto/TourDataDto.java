package org.alongtheblue.alongtheblue_server.domain.tourData.dto;

import lombok.Data;

import java.util.List;

@Data
public class TourDataDto {
    private String contentId;
    private String address;
    private String title;
    private List<String> originimgurl;  // 추가된 필드
    private String introduction;  // 추가된 필드
    private String restDate;   // 추가된 필드
    private String infoCenter;

}
