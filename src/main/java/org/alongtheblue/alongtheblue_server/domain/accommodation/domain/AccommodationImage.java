package org.alongtheblue.alongtheblue_server.domain.accommodation.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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
