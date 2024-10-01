package org.alongtheblue.alongtheblue_server.global.data.alongBlues;

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
    private String x;
    private String y;
    private String category;

    @ManyToOne
    @JoinColumn(name = "blueCourse")
    @JsonBackReference
    private BlueCourse blueCourse;

    @Builder
    public BlueItem(String name, String address, String x, String y, String category) {
        this.name = name;
        this.address = address;
        this.x = x;
        this.y = y;
        this.category = category;
    }
}
