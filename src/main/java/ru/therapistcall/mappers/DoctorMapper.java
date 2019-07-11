package ru.therapistcall.mappers;

import org.modelmapper.ModelMapper;
import ru.therapistcall.dtos.DoctorDto;
import ru.therapistcall.entities.Doctor;

import java.util.List;
import java.util.stream.Collectors;

public class DoctorMapper {

    private static final ModelMapper toDtoMapper = new ModelMapper();
    private static final ModelMapper toEntityMapper = new ModelMapper();

    private static DoctorDto asDto(Doctor doctor) {
        return toDtoMapper.map(doctor, DoctorDto.class);
    }

    public static List<DoctorDto> asDtos(List<Doctor> doctors) {
        return doctors.stream().map(DoctorMapper::asDto).collect(Collectors.toList());
    }


    public static Doctor asEntity(DoctorDto dto) {
        return toEntityMapper.map(dto, Doctor.class);
    }
}