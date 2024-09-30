package org.alongtheblue.alongtheblue_server.global.data.accommodation;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.alongtheblue.alongtheblue_server.global.data.tourcommunity.TourImage;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contentId;
    private String title;
    private String address;
    @Column(columnDefinition = "TEXT")
    private String introduction;
    private String infoCenter;
    private String checkintime;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AccommodationImage> accommodationImage;

    public Accommodation(String contentId, String title, String address) {
        this.contentId = contentId;
        this.title = title;
        this.address = address;
    }
}
