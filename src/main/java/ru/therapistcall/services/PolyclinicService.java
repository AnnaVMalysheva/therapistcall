package ru.therapistcall.services;

import ru.therapistcall.dtos.PolyclinicDto;
import ru.therapistcall.dtos.PolyclinicRequestDto;
import ru.therapistcall.entities.Polyclinic;
import ru.therapistcall.exception.NotFoundException;

import java.io.UnsupportedEncodingException;
import java.util.Set;

public interface PolyclinicService {

    Set<Polyclinic> getPolyclinicsByCoordinates(PolyclinicRequestDto polyclinicRequestDto);

    Set<Polyclinic> getPolyclinicsByAddress(PolyclinicRequestDto polyclinicRequestDto) throws NotFoundException;

    PolyclinicDto addPolyclinic(PolyclinicDto policlinicDto) throws UnsupportedEncodingException;

    PolyclinicDto updatePolyclinic(PolyclinicDto policlinicDto);
}