package org.alongtheblue.alongtheblue_server.global.data.restaurant;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contentId;
    private String title;
    private String addr;
    @Column(columnDefinition = "TEXT")
    private String introduction;
    private String restDate;
    private String infoCenter;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonManagedReference
    List<RestaurantImage> images;

    public Restaurant(String contentId, String title, String addr) {
        this.contentId = contentId;
        this.title = title;
        this.addr = addr;
    }
}
