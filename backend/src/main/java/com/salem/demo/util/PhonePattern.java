package com.salem.demo.util;

public class PhonePattern {

    public PhonePattern(){ }

    public boolean cameroonPatternChecker(String phone) {
        return phone.matches(getCameroonPhonePattern());
    }

    public boolean ethiopiaPatternChecker(String phone) {
        return phone.matches(getEthiopiaPhonePattern());
    }

    public boolean moroccoPatternChecker(String phone) {
        return phone.matches(getMoroccoPhonePattern());
    }

    public boolean mozambiquePatternChecker(String phone) {
        return phone.matches(getMozambiquePhonePattern());
    }

    public boolean ugandaPatternChecker(String phone) {
        return phone.matches(getUgandaPhonePattern());
    }

    public String getCameroonPhonePattern() {
        return "\\(237\\)\\ ?[2368]\\d{7,8}$";
    }

    public String getEthiopiaPhonePattern() {
        return "\\(251\\)\\ ?[1-59]\\d{8}$";
    }

    public String getMoroccoPhonePattern() {
        return "\\(212\\)\\ ?[5-9]\\d{8}$";
    }

    public String getMozambiquePhonePattern() {
        return "\\(258\\)\\ ?[28]\\d{7,8}$";
    }

    public String getUgandaPhonePattern() {
        return "\\(256\\)\\ ?\\d{9}$";
    }

    public boolean phonePatternValidityChecker(String phone){
        return cameroonPatternChecker(phone)
                || ethiopiaPatternChecker(phone)
                || moroccoPatternChecker(phone)
                || mozambiquePatternChecker(phone)
                || ugandaPatternChecker(phone);
    }
}
