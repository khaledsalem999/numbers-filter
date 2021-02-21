package com.salem.demo.interfaces;

import com.salem.demo.entity.Customer;
import com.salem.demo.repository.CustomerRepository;

import java.util.List;

public interface Filters {

    List<Customer> filterByPhoneState(String stateFilter,List<Customer> currentList);

    List<Customer> filterByCountry(String countryFilter,CustomerRepository customerRepository);

}
