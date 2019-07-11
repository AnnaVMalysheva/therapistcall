package ru.therapistcall.controller;

import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.therapistcall.dtos.*;
import ru.therapistcall.exception.NotFoundException;
import ru.therapistcall.mappers.DoctorMapper;
import ru.therapistcall.mappers.PolyclinicMapper;
import ru.therapistcall.services.DoctorService;
import ru.therapistcall.services.PolyclinicService;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
public class TherapistController {

    private final PolyclinicService polyclinicService;

    private final DoctorService doctorService;

    @GetMapping("getNearestPolyclinics")
    public Set<PolyclinicDto> getNearestPolyclinics(@RequestBody PolyclinicRequestDto polyclinicRequestDto) throws NotFoundException {

        if(polyclinicRequestDto.getOwnCoordinates() != null) {
            return PolyclinicMapper.asDtos(polyclinicService.getPolyclinicsByCoordinates(polyclinicRequestDto));
        } else if (!StringUtils.isEmpty(polyclinicRequestDto.getAddress())){
            return PolyclinicMapper.asDtos(polyclinicService.getPolyclinicsByAddress(polyclinicRequestDto));
        } else {
            throw new IllegalArgumentException("Address should be filled");
        }
    }

    @GetMapping("getDoctorsBySpeciality")
    public List<DoctorDto> getDoctorsBySpeciality(@RequestBody DoctorPolyclinicDto doctorRequestDto) {
        return DoctorMapper.asDtos(doctorService.getDoctorBySpeciality(doctorRequestDto.getSpeciality(), doctorRequestDto.getEquipped(),
                doctorRequestDto.getPolyclinicId()));
    }


    @PostMapping("saveDoctor")
    public DoctorDto saveDoctor(@RequestBody DoctorInfoDto doctorInfoDto) {
        if (doctorInfoDto.getDoctorDto().getId() == null) {
            return doctorService.addDoctorRequest(doctorInfoDto);
        }
        return doctorService.updateDoctorRequest(doctorInfoDto);
    }

    @PostMapping("savePolyclinic")
    public PolyclinicDto savePolyclinic(@RequestBody PolyclinicDto policlinicDto) throws UnsupportedEncodingException {
        if (policlinicDto.getId() == null) {
            return polyclinicService.addPolyclinic(policlinicDto);
        }
        return polyclinicService.updatePolyclinic(policlinicDto);
    }

    @GetMapping
    public String health() {
        return "Ok";
    }
}
