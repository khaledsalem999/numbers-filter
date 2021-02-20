package com.salem.demo.controller;

import com.salem.demo.entity.Customer;
import com.salem.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping
    public List<Customer> getCustomers(
            @RequestParam(value = "stateFilter",required = false) String stateFilter,
            @RequestParam(value = "countryFilter",required = false) String countryFilter,
            @RequestParam(value = "page",required = false) int page,
            @RequestParam(value = "size",required = false) int size){
        return customerService.getCustomersList(stateFilter,countryFilter,page,size);
    }
}
