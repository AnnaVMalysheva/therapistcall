package ru.therapistcall.services.impl;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.therapistcall.TherapistCallApplication;
import ru.therapistcall.dtos.*;
import ru.therapistcall.entities.Address;
import ru.therapistcall.entities.Doctor;
import ru.therapistcall.entities.ExaminationAvailability;
import ru.therapistcall.entities.Polyclinic;
import ru.therapistcall.exception.NotFoundException;
import ru.therapistcall.mappers.PolyclinicMapper;
import ru.therapistcall.repositories.PolyclinicRepository;
import ru.therapistcall.services.PolyclinicService;
import ru.therapistcall.yandex_integration.YandexGeocodeApi;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PolyclinicServiceImpl implements PolyclinicService {

    private final PolyclinicRepository polyclinicRepository;

    private final YandexGeocodeApi yandexGeocodeApi;

    private final TherapistCallApplication.YandexKeyHolder yandexKeyHolder;

    private final GeometryFactory geometryFactory = new GeometryFactory();

    @Override
    @Transactional(readOnly = true)
    public Set<Polyclinic> getPolyclinicsByCoordinates(PolyclinicRequestDto polyclinicRequestDto) {
        CoordinatesDto ownCoordinates = polyclinicRequestDto.getOwnCoordinates();
        Point pointByCoordinates = geometryFactory.createPoint(new Coordinate(ownCoordinates.getLongitude(), ownCoordinates.getLatitude()));
        return getPolyclinics(polyclinicRequestDto, pointByCoordinates);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Polyclinic> getPolyclinicsByAddress(PolyclinicRequestDto polyclinicRequestDto) throws NotFoundException {
        CoordinateDto coordinateDto = yandexGeocodeApi.getCoordinatesByGeocode(polyclinicRequestDto.getAddress(),
                yandexKeyHolder.getApiKey(), "json");

        if(coordinateDto.getResponse().getGeoObjectCollection().getMetaDataProperty().getGeocoderResponseMetaData().getFound() > 0) {
            Position position = new Position(coordinateDto.getResponse().getGeoObjectCollection().getFeatureMember().get(0)
                    .getGeoObject().getPoint().getPos());
            Point pointByCoordinates = geometryFactory.createPoint(position.getCoordinate());
            return getPolyclinics(polyclinicRequestDto, pointByCoordinates);
        }
        throw new NotFoundException("There is no address");
    }

    @Override
    public PolyclinicDto addPolyclinic(PolyclinicDto policlinicDto) throws UnsupportedEncodingException {
        String addressLine = policlinicDto.getAddress().getAddressLine();
        CoordinatesDto ownCoordinates = policlinicDto.getAddress().getCoordinates();
        Polyclinic polyclinic = null;

        if (!StringUtils.isEmpty(addressLine)){
            polyclinic = getPolyclinicInfo(policlinicDto, addressLine);
        } else if (!StringUtils.isEmpty(ownCoordinates)){
            if (!StringUtils.isEmpty(ownCoordinates)){
                polyclinic = getPolyclinicInfo(policlinicDto, ownCoordinates.getLatitude().toString() + "," + ownCoordinates.getLongitude().toString());
            }
        }

        if (polyclinic == null) {
            throw new IllegalArgumentException("Incorrect data for polyclinic.");
        }

        return PolyclinicMapper.asDto(polyclinicRepository.save(polyclinic));

    }

    @Override
    public PolyclinicDto updatePolyclinic(PolyclinicDto policlinicDto) {
        return null;
    }

    private Polyclinic getPolyclinicInfo(PolyclinicDto policlinicDto, String geocode) throws UnsupportedEncodingException {

        CoordinateDto geocodeStr = yandexGeocodeApi.getCoordinatesByGeocode(encodeValue(geocode),
                yandexKeyHolder.getApiKey(), "json");
        CoordinateDto.GeoObjectCollectionDto geoObjectCollection = geocodeStr.getResponse().getGeoObjectCollection();
        if (geoObjectCollection.getMetaDataProperty().getGeocoderResponseMetaData().getFound() > 0){
            CoordinateDto.FeatureMemberDto firstFeatureMemberDto = geoObjectCollection.getFeatureMember().get(0);
            Point pointByCoordinates;
            pointByCoordinates = geometryFactory.createPoint(Position.builder().yandexPos(firstFeatureMemberDto.getGeoObject().getPoint().getPos()).build().getCoordinate());
            return Polyclinic.builder().name(policlinicDto.getName()).address(
                    Address.builder().coordinates(pointByCoordinates).addressLine(firstFeatureMemberDto.getGeoObject().getAddressDetails().getCountry().getAddressLine()).build()).build();
        }
        return null;
    }

    private String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }


    private Set<Polyclinic> getPolyclinics(PolyclinicRequestDto polyclinicRequestDto, Point pointByCoordinates) {
        if(polyclinicRequestDto.getDoctorSpeciality() != null) {
            if(polyclinicRequestDto.getEquipped()) {
                return polyclinicRepository.findNearest(pointByCoordinates.getX(), pointByCoordinates.getY())
                        .flatMap(polyclinic -> polyclinic.getAvailabilities().stream())
                        .filter(examinationAvailability ->
                                examinationAvailability.getEquipped()
                                        && examinationAvailability.getExamination() == polyclinicRequestDto.getExaminationType())
                        .map(ExaminationAvailability::getPolyclinic).collect(Collectors.toSet());
            }
            else {
                return polyclinicRepository.findNearest(pointByCoordinates.getX(), pointByCoordinates.getY())
                        .flatMap(polyclinic -> polyclinic.getAvailabilities().stream())
                        .filter(examinationAvailability ->
                                examinationAvailability.getExamination() == polyclinicRequestDto.getExaminationType())
                        .map(ExaminationAvailability::getPolyclinic).collect(Collectors.toSet());
            }
        }
        else {
            if(polyclinicRequestDto.getEquipped()) {
                return polyclinicRepository.findNearest(pointByCoordinates.getX(), pointByCoordinates.getY())
                        .flatMap(polyclinic -> polyclinic.getDoctors().stream())
                        .filter(doctor ->
                                doctor.getEquipped()
                                        && doctor.getSpeciality() == polyclinicRequestDto.getDoctorSpeciality())
                        .map(Doctor::getPolyclinic).collect(Collectors.toSet());
            }
            else {
                return polyclinicRepository.findNearest(pointByCoordinates.getX(), pointByCoordinates.getY())
                        .flatMap(polyclinic -> polyclinic.getDoctors().stream())
                        .filter(doctor ->
                                doctor.getSpeciality() == polyclinicRequestDto.getDoctorSpeciality())
                        .map(Doctor::getPolyclinic).collect(Collectors.toSet());
            }
        }
    }


}