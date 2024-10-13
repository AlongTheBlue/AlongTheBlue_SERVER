package org.alongtheblue.alongtheblue_server.global.data.global;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public abstract class CustomPageMixIn<T> extends PageImpl<T> {
    @JsonProperty("category")
    private String category;

    public CustomPageMixIn(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
