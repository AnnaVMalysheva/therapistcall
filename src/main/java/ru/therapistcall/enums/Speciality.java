package ru.therapistcall.enums;

public enum Speciality {
    IMMUNOLOGY ("иммунолог"),
    SERGERY ("хирург"),
    PEDIATRICS("педиатр"),
    RADIOLOGY("рентгенолог"),
    UROLOGY("уролог"),
    THERAPIST("терапевт");

    private String name;

    Speciality(String name) {
        this.name = name;
    }
}
