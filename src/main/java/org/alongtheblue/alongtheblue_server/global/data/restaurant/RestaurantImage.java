package org.alongtheblue.alongtheblue_server.global.data.restaurant;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class RestaurantImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originimgurl;

    @ManyToOne
    @JoinColumn(name = "restaurant")
    @JsonBackReference
    private Restaurant restaurant;

    public RestaurantImage(Restaurant restaurant, String originimgurl) {
        this.restaurant= restaurant;
        this.originimgurl= originimgurl;
    }

}
