package com.salem.demo;

import com.salem.demo.entity.Customer;
import com.salem.demo.service.CustomerService;
import com.salem.demo.util.PhonePattern;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private CustomerService customerService;
	private final PhonePattern phonePattern = new PhonePattern();

	@Test
	void checkIfStateValidFilterWorking(){
		List<Customer> validList = customerService.getCustomersList("valid",null,0,99);
		Assert.assertFalse(validList.isEmpty());
		for (Customer customer: validList) {
			Assert.assertTrue(phonePattern.phonePatternValidityChecker(customer.getPhone()));
		}
	}

	@Test
	void checkIfStateInvalidFilterWorking(){
		List<Customer> invalidList = customerService.getCustomersList("invalid",null,0,99);
		Assert.assertFalse(invalidList.isEmpty());
		for (Customer customer: invalidList) {
			Assert.assertFalse(phonePattern.phonePatternValidityChecker(customer.getPhone()));
		}
	}

	@Test
	void checkIfCountryFilterWorking(){
		List<Customer> cameroonList =
				customerService.getCustomersList("valid","cameroon",0,99);
		Assert.assertFalse(cameroonList.isEmpty());
		for (Customer customer: cameroonList){
			Assert.assertTrue(phonePattern.cameroonPatternChecker(customer.getPhone()));
		}
	}

	@Test
	void checkMultipleCountriesFilterWorking(){
		List<Customer> cameroonAndEthiopiaList =
				customerService.getCustomersList("valid","cameroon&ethiopia",0,99);
		for (Customer customer: cameroonAndEthiopiaList){
			Assert.assertTrue(phonePattern.cameroonPatternChecker(customer.getPhone())
					|| phonePattern.ethiopiaPatternChecker(customer.getPhone()) );
		}
	}

	@Test
	void checkCountriesWithInvalidStateFilterWorking(){
		List<Customer> cameroonAndEthiopiaInvalidList =
				customerService.getCustomersList("invalid","cameroon&ethiopia",0,99);
		for (Customer customer: cameroonAndEthiopiaInvalidList){
			Assert.assertFalse(phonePattern.cameroonPatternChecker(customer.getPhone())
					|| phonePattern.ethiopiaPatternChecker(customer.getPhone()) );
		}
	}


	@Test
	void checkPagination(){
		List<Customer> paginatedList =
				customerService.getCustomersList(null,null,0,5);
		Assert.assertEquals(5, paginatedList.size());
		List<Customer> paginatedListSecondPage =
				customerService.getCustomersList(null,null,1,5);
		Assert.assertEquals(5,paginatedListSecondPage.get(0).getId());
		Assert.assertEquals(6,paginatedListSecondPage.get(1).getId());
		Assert.assertEquals(7,paginatedListSecondPage.get(2).getId());
		Assert.assertEquals(8,paginatedListSecondPage.get(3).getId());
		Assert.assertEquals(9,paginatedListSecondPage.get(4).getId());
	}



}
