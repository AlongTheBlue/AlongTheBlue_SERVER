package org.alongtheblue.alongtheblue_server.global.data.tourcommunity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

public record TourPostItemRequestDto(
        String title,
        String category,
        String address,
        String comment
//        String contentsId
) {

}