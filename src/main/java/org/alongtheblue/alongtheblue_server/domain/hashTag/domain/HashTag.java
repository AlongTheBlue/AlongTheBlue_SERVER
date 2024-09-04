package org.alongtheblue.alongtheblue_server.domain.hashTag.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.alongtheblue.alongtheblue_server.domain.hashTag_course.domain.HashTag_Course;
import org.alongtheblue.alongtheblue_server.domain.hashTag_item.domain.HashTag_item;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String content;

    @OneToMany(mappedBy = "hashTag", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<HashTag_item> hashTagItems = new ArrayList<>();

    @OneToMany(mappedBy = "hashTag", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<HashTag_Course> hashTagItems = new ArrayList<>();

    @Builder
    public HashTag(String content) {
        this.content = content;
    }
}
