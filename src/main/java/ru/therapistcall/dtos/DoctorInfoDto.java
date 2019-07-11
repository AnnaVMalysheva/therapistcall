package ru.therapistcall.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DoctorInfoDto {
    DoctorDto doctorDto;
    DoctorPolyclinicDto doctorPolyclinicDto;
}
