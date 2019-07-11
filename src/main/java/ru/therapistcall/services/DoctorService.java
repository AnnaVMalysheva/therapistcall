package ru.therapistcall.services;

import ru.therapistcall.dtos.DoctorDto;
import ru.therapistcall.dtos.DoctorInfoDto;
import ru.therapistcall.entities.Doctor;
import ru.therapistcall.enums.Speciality;

import java.util.List;

public interface DoctorService {

    List<Doctor> getDoctorBySpeciality(Speciality speciality, Boolean equipped, Long polyclinicId);

    DoctorDto addDoctorRequest(DoctorInfoDto doctorInfoDto);

    DoctorDto updateDoctorRequest(DoctorInfoDto doctorInfoDto);
}
