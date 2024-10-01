package org.alongtheblue.alongtheblue_server.global.data.alongBlues;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public record BlueResponseDto(
        Long id,
        String name,
        String xMap,
        String yMap,
        String address,
        String city
) {

}
