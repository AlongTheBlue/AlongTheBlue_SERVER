package org.alongtheblue.alongtheblue_server.domain.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomPage<T> extends PageImpl<T> {
    @JsonProperty("category")
    private String category;

    public CustomPage() {
        super(List.of());
    }
    public CustomPage(List<T> content, Pageable pageable, long total, String category) {
        super(content, pageable, total);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
