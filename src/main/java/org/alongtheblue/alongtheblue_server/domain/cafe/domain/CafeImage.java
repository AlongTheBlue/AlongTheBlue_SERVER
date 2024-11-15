package org.alongtheblue.alongtheblue_server.domain.cafe.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class CafeImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originimgurl;

    @ManyToOne
    @JoinColumn(name = "cafe")
    @JsonBackReference
    private Cafe cafe;

    public CafeImage(Cafe cafe, String originimgurl) {
        this.cafe= cafe;
        this.originimgurl= originimgurl;
    }
}
