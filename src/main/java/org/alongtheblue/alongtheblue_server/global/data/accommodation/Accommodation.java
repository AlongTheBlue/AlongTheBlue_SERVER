package org.alongtheblue.alongtheblue_server.global.data.accommodation;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.alongtheblue.alongtheblue_server.global.data.tourcommunity.TourImage;

import java.util.List;

@Entity
@Data
public class Accommodation {
    @Id
    private String contentsid;
    private String title;
    private String roadaddress;
    @Column(columnDefinition = "TEXT")
    private String introduction;
    private String infocenter;
    private String checkintime;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AccommodationImage> accommodationImage;
}
