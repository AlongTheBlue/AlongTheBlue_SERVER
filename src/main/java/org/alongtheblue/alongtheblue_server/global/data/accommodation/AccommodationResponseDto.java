package org.alongtheblue.alongtheblue_server.global.data.accommodation;

public record AccommodationResponseDto (
    String address,
    String title,
    String contentid,
    String img,
    String xMap,
    String yMap,
    String category
){}
