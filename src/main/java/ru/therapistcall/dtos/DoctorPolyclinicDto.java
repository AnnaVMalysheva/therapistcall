package ru.therapistcall.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.therapistcall.enums.Speciality;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DoctorPolyclinicDto {

    private Long polyclinicId;

    private Speciality speciality;

    private Boolean equipped;
}
