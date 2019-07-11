package ru.therapistcall.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.therapistcall.enums.Examination;
import ru.therapistcall.enums.Speciality;

@Data
@NoArgsConstructor
public class PolyclinicRequestDto {

    private String address;

    private CoordinatesDto ownCoordinates;

    private Speciality doctorSpeciality;

    private Examination examinationType;

    private Boolean equipped;
}