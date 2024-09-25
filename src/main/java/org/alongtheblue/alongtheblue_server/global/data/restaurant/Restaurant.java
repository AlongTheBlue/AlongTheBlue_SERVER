package org.alongtheblue.alongtheblue_server.global.data.restaurant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contentId;
    private String title;
    private String cat1;
    private String cat2;
    private String cat3;
    private String addr1;
    private String addr2;
    private String areaCode;

    public Restaurant(String contentId, String title, String cat1, String cat2, String cat3, String addr1, String addr2, String areaCode) {
        this.contentId = contentId;
        this.title = title;
        this.cat1 = cat1;
        this.cat2 = cat2;
        this.cat3 = cat3;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.areaCode = areaCode;
    }
}
