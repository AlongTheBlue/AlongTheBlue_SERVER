package org.alongtheblue.alongtheblue_server.domain.blue.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Blue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String xMap;
    private String yMap;
    private String address;
    private String city;

    @OneToMany(mappedBy = "blue", cascade = CascadeType.ALL)
    @JsonManagedReference
    List<BlueImage> blueImages;
}
