package org.alongtheblue.alongtheblue_server.global.data.accommodation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.alongtheblue.alongtheblue_server.global.data.tourcommunity.TourImage;
import org.alongtheblue.alongtheblue_server.global.data.tourcommunity.TourPostItem;

import java.util.List;

@Entity
@Data
public class AccommodationImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String url;

    @ManyToOne
    @JoinColumn(name = "Accommodation")
    @JsonBackReference
    private Accommodation accommodation;

}
