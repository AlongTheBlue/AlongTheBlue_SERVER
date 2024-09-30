package org.alongtheblue.alongtheblue_server.global.data.accommodation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alongtheblue.alongtheblue_server.global.data.restaurant.Restaurant;
import org.alongtheblue.alongtheblue_server.global.data.tourcommunity.TourImage;
import org.alongtheblue.alongtheblue_server.global.data.tourcommunity.TourPostItem;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class AccommodationImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String originimgurl;

    @ManyToOne
    @JoinColumn(name = "Accommodation")
    @JsonBackReference
    private Accommodation accommodation;

    public AccommodationImage(Accommodation accommodation, String originimgurl) {
        this.accommodation= accommodation;
        this.originimgurl= originimgurl;
    }
}
