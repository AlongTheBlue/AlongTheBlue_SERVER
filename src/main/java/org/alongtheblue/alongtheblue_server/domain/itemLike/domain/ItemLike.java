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

    private Date date;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userId", nullable = false)
    @JsonBackReference
    private UserInfo userInfo;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "itemId", nullable = false)
    @JsonBackReference
    private Item item;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ItemLikeId implements Serializable {
        private Long userId;
        private Long itemId;
    }
}
