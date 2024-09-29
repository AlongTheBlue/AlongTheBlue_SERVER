package org.alongtheblue.alongtheblue_server.global.data.accommodation;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccommodationDTO {
    private String contentsid;
    private String roadaddress;
    private String title;
    private List<String> originimgurl;  // 추가된 필드
    private String introduction;  // 추가된 필드
    private String checkintime;   // 추가된 필드
    private String infocenter;
}
