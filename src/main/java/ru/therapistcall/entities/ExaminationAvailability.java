package ru.therapistcall.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.therapistcall.enums.Examination;

import javax.persistence.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "examination_availability")
public class ExaminationAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "examination_id_gen")
    @SequenceGenerator(name = "examination_id_gen", sequenceName = "examination_id_seq", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Examination examination;

    private Boolean equipped;

    @ManyToOne
    private Polyclinic polyclinic;
}