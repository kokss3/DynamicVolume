package com.example.koks.dynamicvolume;

public enum Devices {
    ABM(0xA4, "Air bag module"),
    ANZV(0xE7, "Front display"),
    BMBT(0xF0, "On-board monitor operating part"),
    CCM(0x30, "Check control module"),
    CDC(0x18, "CD Changer"),
    CDCD(0x76, "CD changer, DIN size"),
    CID(0x46, "Central information display "),
    DIA(0x3F, "Diagnostic"),
    DSP(0x6A, "Digital signal processing audio amplifier"),
    EWS(0x44, "Immobiliser"),
    FBZV(0x40, "Remote control central locking"),
    FMID(0xA0, "Rear multi-info-display"),
    FUH(0x28, "Radio controlled clock"),
    GLO(0xBF, "Global, broadcast address"),
    GM(0x00, "Radio"),
    GT(0x3B, "Graphics driver"),
    GTF(0x43, "Graphics driver for rear screen"),
    IHK(0x5B, "Integrated heating and air conditioning"),
    IKE(0x80, "Instrument cluster electronics"),
    IRIS(0xE0, "Integrated radio information system"),
    LCM(0xD0, "Light control module"),
    LOC(0xFF, "Local"),
    MFL(0x50, "Multi function steering wheel"),
    MID(0xC0, "Multi-info display"),
    MM(0x51, "Mirror memory "),
    NAV(0x7F, "Navigation"),
    PDC(0x60, "Park distance control"),
    RAD(0x68, "Radio"),
    SES(0xB0, "Speed recognition system"),
    SM(0x72, "Seat memory"),
    TEL(0xC8, "Telephone"),
    TV(0xED, "Television");

    private int number;
    private String name;
    Devices(int number, String name) {
        this.name = name;
        this.number = number;
    }
}