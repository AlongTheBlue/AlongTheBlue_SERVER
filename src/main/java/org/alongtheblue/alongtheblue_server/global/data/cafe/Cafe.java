package org.alongtheblue.alongtheblue_server.global.data.cafe;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Cafe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contentId;
    private String title;
    private String address;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    private String restDate;
    private String infoCenter;
    private String xMap;
    private String yMap;

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL)
    @JsonManagedReference
    List<CafeImage> images;

    public Cafe(String contentId, String title, String address, String xMap, String yMap) {
        this.contentId = contentId;
        this.title = title;
        this.address = address;
        this.xMap = xMap;
        this.yMap = yMap;
    }
}
