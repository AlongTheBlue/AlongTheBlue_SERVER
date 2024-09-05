package org.alongtheblue.alongtheblue_server.domain.itemLike.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.alongtheblue.alongtheblue_server.domain.hashTag.domain.HashTag;
import org.alongtheblue.alongtheblue_server.domain.hashTag_item.domain.HashTag_item;
import org.alongtheblue.alongtheblue_server.domain.item.domain.Item;
import org.alongtheblue.alongtheblue_server.domain.userInfo.domain.UserInfo;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemLike {
    @EmbeddedId
    private ItemLike.ItemLikeId id;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "itemId", nullable = false)
    @JsonBackReference
    private UserInfo user;

    @ManyToOne
    @MapsId("hid")
    @JoinColumn(name = "hid", nullable = false)
    @JsonBackReference
    private HashTag hashTag;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ItemLikeId implements Serializable {
        private Long itemId;
        private Long hid;
    }
}
