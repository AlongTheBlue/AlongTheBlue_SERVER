package org.alongtheblue.alongtheblue_server.domain.alongBlues.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
public class BlueItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String xMap;
    private String yMap;
    private String category;

    @ManyToOne
    @JoinColumn(name = "blueCourse")
    @JsonBackReference
    private BlueCourse blueCourse;

    @Builder
    public BlueItem(String name, String address, String xMap, String yMap, String category) {
        this.name = name;
        this.address = address;
        this.xMap = xMap;
        this.yMap = yMap;
        this.category = category;
    }
}
