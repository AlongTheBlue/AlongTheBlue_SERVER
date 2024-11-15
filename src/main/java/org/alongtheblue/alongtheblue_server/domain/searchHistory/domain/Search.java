package org.alongtheblue.alongtheblue_server.domain.searchHistory.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.alongtheblue.alongtheblue_server.domain.userInfo.domain.UserInfo;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Search {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String content;

    @NotNull
    private Date date;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    @JsonBackReference
    private UserInfo userInfo;

    @Builder
    public Search(String content, Date date) {
        this.content = content;
        this.date = date;
    }
}
