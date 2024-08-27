package org.alongtheblue.alongtheblue_server.domain.hashTag_item.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.alongtheblue.alongtheblue_server.domain.hashTag.domain.HashTag;
import org.alongtheblue.alongtheblue_server.domain.item.domain.Item;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HashTag_item {

    @EmbeddedId
    private HashTagItemId id;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "itemId", nullable = false)
    @JsonBackReference
    private Item item;

    @ManyToOne
    @MapsId("hid")
    @JoinColumn(name = "hid", nullable = false)
    @JsonBackReference
    private HashTag hashTag;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class HashTagItemId implements Serializable {
        private Long itemId;
        private Long hid;
    }
}
