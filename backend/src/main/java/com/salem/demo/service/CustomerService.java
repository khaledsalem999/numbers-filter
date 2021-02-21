package com.salem.demo.service;

import com.salem.demo.entity.Customer;
import com.salem.demo.interfaces.Filters;
import com.salem.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import com.salem.demo.util.PhonePattern;
import com.salem.demo.enums.Country;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService implements Filters {

    @Autowired
    private CustomerRepository customerRepository;
    private final PhonePattern phonePattern = new PhonePattern();

    public List<Customer> getCustomersList(String stateFilter, String countryFilter, int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        List<Customer> filteredCustomerList = new ArrayList<>();
        stateFilter = stateFilter == null ? "all" : stateFilter;

        if(countryFilter != null){
            filteredCustomerList.addAll(filterByPhoneState(stateFilter,filterByCountry(countryFilter)));
        }else{
            filteredCustomerList.addAll(filterByPhoneState(stateFilter,customerRepository.findAll()));
        }

        long start = pageable.getOffset();
        long end = (start + pageable.getPageSize()) > filteredCustomerList.size() ? filteredCustomerList.size()
                : (start + pageable.getPageSize());
        return filteredCustomerList.subList((int)start,(int)end);
    }

    @Override
    public List<Customer> filterByPhoneState(String stateFilter,List<Customer> currentList){
        List<Customer> filteredByStateCustomerList = new ArrayList<>();
        for (Customer customer: currentList) {
            switch (stateFilter){
                case "all":
                    return currentList;
                case "valid":
                    if (phonePattern.phonePatternValidityChecker(customer.getPhone())) {
                        filteredByStateCustomerList.add(customer);
                    }
                    break;
                case "invalid":
                    if(!phonePattern.phonePatternValidityChecker(customer.getPhone())){
                        filteredByStateCustomerList.add(customer);
                    }
                    break;
            }
        }
        return filteredByStateCustomerList;
    }

    @Override
    public List<Customer> filterByCountry(String countryFilter){
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
