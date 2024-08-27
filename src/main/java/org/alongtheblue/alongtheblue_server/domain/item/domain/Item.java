package org.alongtheblue.alongtheblue_server.domain.item.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.alongtheblue.alongtheblue_server.domain.hashTag_item.domain.HashTag_item;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String categoryId;

    @NotNull
    private Long likeCount;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<HashTag_item> hashTagItems = new ArrayList<>();

    @Builder
    public Item(String categoryId, Long likeCount) {
        this.categoryId = categoryId;
        this.likeCount = likeCount;
    }
}
