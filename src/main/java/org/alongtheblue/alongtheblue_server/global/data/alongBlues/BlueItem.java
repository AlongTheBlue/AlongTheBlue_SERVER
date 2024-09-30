package org.alongtheblue.alongtheblue_server.global.data.alongBlues;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
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
}
