package com.salem.demo.service;

import com.salem.demo.entity.Customer;
import com.salem.demo.interfaces.Filters;
import com.salem.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.salem.demo.util.FilterHandler;
import com.salem.demo.enums.Country;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    private final FilterHandler filterHandler = new FilterHandler();

    public List<Customer> getCustomersList(String stateFilter, String countryFilter, int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        List<Customer> filteredCustomerList = new ArrayList<>();
        stateFilter = stateFilter == null ? "all" : stateFilter;

        if(countryFilter != null){
            filteredCustomerList.addAll(filterHandler.filterByPhoneState(stateFilter,filterHandler
                    .filterByCountry(countryFilter,customerRepository)));
        }else{
            filteredCustomerList.addAll(filterHandler.filterByPhoneState(stateFilter,customerRepository.findAll()));
        }

        long start = pageable.getOffset();
        long end = (start + pageable.getPageSize()) > filteredCustomerList.size() ? filteredCustomerList.size()
                : (start + pageable.getPageSize());
        return filteredCustomerList.subList((int)start,(int)end);
    }

}
