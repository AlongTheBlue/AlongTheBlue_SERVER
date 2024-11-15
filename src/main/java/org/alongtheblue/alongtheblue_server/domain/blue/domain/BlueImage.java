package org.alongtheblue.alongtheblue_server.domain.blue.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class BlueImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "blue")
    @JsonBackReference
    private Blue blue;

}
