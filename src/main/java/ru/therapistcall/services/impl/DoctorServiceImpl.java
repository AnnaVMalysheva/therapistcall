package ru.therapistcall.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.therapistcall.dtos.DoctorDto;
import ru.therapistcall.dtos.DoctorInfoDto;
import ru.therapistcall.dtos.DoctorPolyclinicDto;
import ru.therapistcall.entities.Doctor;
import ru.therapistcall.entities.Polyclinic;
import ru.therapistcall.enums.Speciality;
import ru.therapistcall.mappers.DoctorMapper;
import ru.therapistcall.repositories.DoctorRepository;
import ru.therapistcall.repositories.PolyclinicRepository;
import ru.therapistcall.services.DoctorService;

import java.util.List;

@Service
@AllArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    private final PolyclinicRepository polyclinicRepository;


    @Override
    @Transactional(readOnly = true)
    public List<Doctor> getDoctorBySpeciality(Speciality speciality, Boolean equipped, Long polyclinicId) {
        Polyclinic polyclinic = polyclinicRepository.findById(polyclinicId).get();
        if(equipped) {
            return doctorRepository.getByPolyclinicAndSpecialityAndEquipped(polyclinic, speciality, equipped);
        }
        return doctorRepository.getByPolyclinicAndSpeciality(polyclinic, speciality);
    }


    @Override
    @Transactional
    public DoctorDto addDoctorRequest(DoctorInfoDto doctorInfoDto) {
        DoctorPolyclinicDto doctorPolyclinicDto = doctorInfoDto.getDoctorPolyclinicDto();
        DoctorDto doctorDto = doctorInfoDto.getDoctorDto();
        Polyclinic polyclinic = polyclinicRepository.findById(doctorPolyclinicDto.getPolyclinicId()).orElseThrow(() -> new IllegalArgumentException("There is no such polyclinic"));
        if (polyclinic !=null) {
            Doctor doctor = DoctorMapper.asEntity(doctorDto);
            doctor.setSpeciality(doctorPolyclinicDto.getSpeciality());
            doctor.setPolyclinic(polyclinic);
            doctorRepository.save(doctor);
            return doctorDto;
        } else {
            throw new IllegalArgumentException("Incorrect polyclinic data");
        }
    }

    @Override
    public DoctorDto updateDoctorRequest(DoctorInfoDto doctorInfoDto) {
        DoctorPolyclinicDto doctorPolyclinicDto = doctorInfoDto.getDoctorPolyclinicDto();
        DoctorDto doctorDto = doctorInfoDto.getDoctorDto();
        Polyclinic polyclinic = polyclinicRepository.findById(doctorPolyclinicDto.getPolyclinicId()).orElseThrow(() -> new IllegalArgumentException("There is no such polyclinic"));
        Doctor doctor = doctorRepository.findById(doctorDto.getId()).orElseThrow(() -> new IllegalArgumentException("There is no such polyclinic"));
        doctor.setPolyclinic(polyclinic);
        doctor.setSpeciality(doctorPolyclinicDto.getSpeciality());
        doctor.setEquipped(doctorPolyclinicDto.getEquipped());
        doctor.setFirstName(doctorDto.getFirstName());
        doctor.setSecondName(doctorDto.getSecondName());
        doctor.setLastName(doctorDto.getLastName());
        doctorRepository.save(doctor);
        return doctorDto;
    }
}
