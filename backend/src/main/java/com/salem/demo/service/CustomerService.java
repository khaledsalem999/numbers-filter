package com.salem.demo.service;

import com.salem.demo.entity.Customer;
import com.salem.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.salem.demo.util.PhonePattern;
import com.salem.demo.enums.Country;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    private final PhonePattern phonePattern = new PhonePattern();

    public List<Customer> getCustomersList(String stateFilter, String countryFilter,int page,int size){
        Pageable pageable = PageRequest.of(page,size);
        List<Customer> filteredCustomerList = new ArrayList<>();
        List<Customer> stateFiltered = new ArrayList<>();
        List<Customer> countryFiltered = new ArrayList<>();
        boolean isCountryFiltered = false;
        if(countryFilter != null){
            String[] listOfCountries = countryFilter.split("&");
            for (String country: listOfCountries){
                if(country.isEmpty() || country.isBlank()){
                    continue;
                }
                countryFiltered.addAll(filterByCountry(country));
            }
            if(stateFilter == null){
                filteredCustomerList.addAll(countryFiltered);
            }else {
                isCountryFiltered = true;
            }
        }
        if(stateFilter != null){
            if(!isCountryFiltered){
                stateFiltered.addAll(filterByPhoneState(stateFilter,customerRepository.findAll()));
                filteredCustomerList.addAll(stateFiltered);
            }else{
                stateFiltered.addAll(filterByPhoneState(stateFilter,countryFiltered));
                filteredCustomerList.addAll(stateFiltered);
            }
        }
        if(stateFilter == null && countryFilter == null){
            filteredCustomerList = customerRepository.findAll();
        }
        long start = pageable.getOffset();
        long end = (start + pageable.getPageSize()) > filteredCustomerList.size() ? filteredCustomerList.size()
                : (start + pageable.getPageSize());
        return filteredCustomerList.subList((int)start,(int)end);
    }

    private List<Customer> filterByPhoneState(String stateFilter,List<Customer> currentList){
        List<Customer> filteredByStateCustomerList = new ArrayList<>();
        for (Customer customer: currentList) {
            if(stateFilter.equals("invalid")){
                if(!phonePattern.phonePatternValidityChecker(customer.getPhone())){
                    filteredByStateCustomerList.add(customer);
                }
            }else if(stateFilter.equals("valid")){
                if(phonePattern.phonePatternValidityChecker(customer.getPhone())){
                    filteredByStateCustomerList.add(customer);
                }
            }
        }
        return filteredByStateCustomerList;
    }

    private List<Customer> filterByCountry(String countryFilter){
        List<Customer> filteredByStateCustomerList = new ArrayList<>();
        Country country = Country.valueOf(countryFilter.toUpperCase());
        switch (country){
            case CAMEROON:
                filteredByStateCustomerList.addAll(customerRepository
                        .findAllByPhoneContaining(Country.CAMEROON.code));
                break;
            case ETHIOPIA:
                filteredByStateCustomerList.addAll(customerRepository
                        .findAllByPhoneContaining(Country.ETHIOPIA.code));
                break;
            case MOROCCO:
                filteredByStateCustomerList.addAll(customerRepository
                        .findAllByPhoneContaining(Country.MOROCCO.code));
                break;
            case MOZAMBIQUE:
                filteredByStateCustomerList.addAll(customerRepository
                        .findAllByPhoneContaining(Country.MOZAMBIQUE.code));
                break;
            case UGANDA:
                filteredByStateCustomerList.addAll(customerRepository
                        .findAllByPhoneContaining(Country.UGANDA.code));
                break;
        }
        return filteredByStateCustomerList;
    }

}
