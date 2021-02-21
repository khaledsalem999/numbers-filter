package com.salem.demo.interfaces;

import com.salem.demo.entity.Customer;

import java.util.List;

public interface Filters {

    List<Customer> filterByPhoneState(String stateFilter,List<Customer> currentList);

    List<Customer> filterByCountry(String countryFilter);

}
