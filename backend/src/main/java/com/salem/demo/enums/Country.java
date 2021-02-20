package com.salem.demo.enums;

public enum Country {
    CAMEROON ("(237)"),
    ETHIOPIA ("(251)"),
    MOROCCO ("(212)"),
    MOZAMBIQUE ("(258)"),
    UGANDA ("(256)");

    public final String code;

    Country(String code) {
        this.code = code;
    }
}
