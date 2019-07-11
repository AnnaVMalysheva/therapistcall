package ru.therapistcall.entities;

import lombok.*;


import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table
@EqualsAndHashCode(exclude = {"doctors", "availabilities"})
public class Polyclinic {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "polyclinic_id_gen")
    @SequenceGenerator(name = "polyclinic_id_gen", sequenceName = "polyclinic_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    @Embedded
    private Address address;

    private Boolean equipped;

    @OneToMany(mappedBy = "polyclinic", fetch = FetchType.EAGER)
    private Set<Doctor> doctors;

    @OneToMany(mappedBy = "polyclinic", fetch = FetchType.EAGER)
    private Set<ExaminationAvailability> availabilities;
}