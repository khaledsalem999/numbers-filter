package com.salem.demo.util;

import com.salem.demo.enums.Country;

public class PhonePattern {

    public PhonePattern(){ }

    public boolean phonePatternValidityChecker(String phone){
        for(Country country: Country.values()){
            if(phone.matches(country.regex)){
                return true;
            }
        }
        return false;
    }
}
