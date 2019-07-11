package ru.therapistcall.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CoordinatesDto {

    private Double latitude;

    private Double longitude;
}