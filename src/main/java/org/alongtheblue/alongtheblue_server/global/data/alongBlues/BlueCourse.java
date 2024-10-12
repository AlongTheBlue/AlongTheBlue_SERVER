package org.alongtheblue.alongtheblue_server.global.data.alongBlues;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
public class BlueCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Date createDay;

    @OneToMany(mappedBy = "blueCourse", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<BlueItem> blueItems;

    @Builder
    public BlueCourse(String title, Date createDay, List<BlueItem> blueItems) {
        this.title = title;
        this.createDay = createDay;
        this.blueItems = blueItems;
    }
}
