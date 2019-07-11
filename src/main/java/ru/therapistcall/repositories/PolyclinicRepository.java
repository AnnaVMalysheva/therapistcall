package ru.therapistcall.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.therapistcall.entities.Polyclinic;

import java.util.stream.Stream;

public interface PolyclinicRepository extends JpaRepository<Polyclinic, Long> {

    @Query(value = "select * from polyclinic p  ORDER BY ST_Distance(ST_Point(?1, ?2), p.coordinate) ASC", nativeQuery = true)
    Stream<Polyclinic> findNearest(@Param("longitude")Double longitude, @Param("latitude")Double latitude);
}