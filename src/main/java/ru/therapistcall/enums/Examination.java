package ru.therapistcall.enums;

public enum Examination {
    COLONOSCOPY ("Колоноскопия"),
    ULTRASOUND_EXAMINATION("узи"),
    FGDS("фгдс"),
    MAMMOGRAPHY("маммография"),
    FLUORIGRAPHY("флюрография");

    private String name;

    Examination(String name) {
        this.name = name;
    }
}