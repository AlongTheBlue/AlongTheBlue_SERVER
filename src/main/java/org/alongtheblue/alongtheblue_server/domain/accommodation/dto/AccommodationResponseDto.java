package org.alongtheblue.alongtheblue_server.domain.accommodation.dto;

public record AccommodationResponseDto (
    String address,
    String title,
    String contentid,
    String img,
    String xMap,
    String yMap,
    String category
){}
