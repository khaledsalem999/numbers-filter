package com.salem.demo.util;

import com.salem.demo.entity.Customer;
import com.salem.demo.enums.Country;
import com.salem.demo.interfaces.Filters;
import com.salem.demo.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

public class FilterHandler implements Filters {

    public FilterHandler(){ }

    public boolean phonePatternValidityChecker(String phone){
        for(Country country: Country.values()){
            if(phone.matches(country.regex)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Customer> filterByPhoneState(String stateFilter, List<Customer> currentList) {
        List<Customer> filteredByStateCustomerList = new ArrayList<>();
        for (Customer customer: currentList) {
            switch (stateFilter){
                case "all":
                    return currentList;
                case "valid":
                    if (phonePatternValidityChecker(customer.getPhone())) {
                        filteredByStateCustomerList.add(customer);
                    }
                    break;
                case "invalid":
                    if(!phonePatternValidityChecker(customer.getPhone())){
                        filteredByStateCustomerList.add(customer);
                    }
                    break;
            }
        }
        return filteredByStateCustomerList;
    }

    @Override
    public List<Customer> filterByCountry(String countryFilter, CustomerRepository customerRepository) {
        List<Customer> countryFiltered = new ArrayList<>();
        String[] listOfCountries = countryFilter.split("&");
        for (String country: listOfCountries){
            if(country.isEmpty() || country.isBlank()){
                continue;
            }
            System.out.println(country);
            countryFiltered.addAll(customerRepository
                    .findAllByPhoneContaining(Country
                            .valueOf(country.toUpperCase()).code));
        }
        return countryFiltered;
    }
}
