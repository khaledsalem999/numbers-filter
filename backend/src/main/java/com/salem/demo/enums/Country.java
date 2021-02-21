package com.salem.demo.enums;

public enum Country {
    CAMEROON ("(237)","\\(237\\)\\ ?[2368]\\d{7,8}$"),
    ETHIOPIA ("(251)","\\(251\\)\\ ?[1-59]\\d{8}$"),
    MOROCCO ("(212)","\\(212\\)\\ ?[5-9]\\d{8}$"),
    MOZAMBIQUE ("(258)","\\(258\\)\\ ?[28]\\d{7,8}$"),
    UGANDA ("(256)","\\(256\\)\\ ?\\d{9}$");

    public final String code;
    public final String regex;

    Country(String code,String regex) {
        this.code = code;
        this.regex = regex;
    }
}
