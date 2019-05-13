package com.yakov.coupons.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.yakov.coupons.dao.CustomerDao;
import com.yakov.coupons.entity.Customer;
import com.yakov.coupons.exceptions.ApplicationException;
import com.yakov.coupons.enums.ErrorType;
import com.yakov.coupons.utils.DateUtils; 
import com.yakov.coupons.utils.validationUtils;

/**
 * Logics for Customer object.
 * @author Yakov.
 *
 */
@Controller
public class CustomerController {

	@Autowired private CustomerDao customerDao;
	
	/**
	 * Validates customer and either creates it in DB or throws exception if validation fails.
	 * @param customer customer to validate
	 * @throws ApplicationException
	 */
	public void createCustomer(Customer customer) throws ApplicationException{
		validateCustomerPassword(customer.getCustomerPassword());
		validateCustomerName(customer.getCustomerName());
		this.customerDao.createCustomer(customer);
	
	}

	/**
	 * Validates name of the customer.
	 * @param name name to validate
	 * @throws ApplicationException
	 */
	private void validateCustomerName(String name) throws ApplicationException{
		validationUtils.validateNameSpelling(name);
		validationUtils.validateAbsence(name);
		validateNameDoNotExistYet(name);
	}

	/**
	 * Validates uniqueness of customer Name.
	 * @param customerName name to validate
	 * @throws ApplicationException
	 */
	private void validateNameDoNotExistYet(String customerName) throws ApplicationException {
		if(customerDao.getCustomerByCustomerName(customerName) != null) {
			throw new ApplicationException(ErrorType.NAME_ALREADY_EXISTS, DateUtils.getCurrentDateAndTime() + 
					"\nAttempt to set customer name using a name that already exists.");
		}
	}
	
	/**
	 * Validates customer password.
	 * @param customerPassword password to validate.
	 * @throws ApplicationException
	 */
	private void validateCustomerPassword(String customerPassword) throws ApplicationException{
		validationUtils.validateAbsence(customerPassword);
		validationUtils.validatePasswordSpelling(customerPassword);
	}	
	
	/**
	 * Searches for customer by id.
	 * @param id id to search for
	 * @return customer that was found
	 * @throws ApplicationException
	 */
	public Customer getCustomerByCustomerId(long id) throws ApplicationException {
		Customer customer = customerDao.getCustomerByCustomerId(id);
	/*	if(customer == null) {
			throw new ApplicationException(ErrorType.NOT_FOUND_IN_DB, DateUtils.getCurrentDateAndTime() + 
					" Get customer by id has failed."
					+ "\nSearch for customer Id  that doesn't exist.");
		}
	*/	return customer;
	}
	
	/**
	 * Searches for customer by name.
	 * @param name name to search for
	 * @return customer that was found
	 * @throws ApplicationException
	 */
	public Customer getCustomerByCustomerName(String name) throws ApplicationException {
		Customer customer = customerDao.getCustomerByCustomerName(name);
	/*	if(customer == null) {
			throw new ApplicationException(ErrorType.NOT_FOUND_IN_DB, DateUtils.getCurrentDateAndTime() + 
					" Get customer by name has failed."
					+ "\nSearch for Customer name  that doesn't exist.");
		}
	*/	return customer;
	}

	/**
	 * Gets List of all customers that exist in DB.
	 * @return list of customers that were found.
	 * @throws ApplicationException
	 */
	public List<Customer> getAllCustomers() throws ApplicationException {
		return customerDao.getAllCustomers();
	}
	
	/**
	 * Deletes customer and all of its coupons from DB or throws exception if no customer found by provided id.
	 * @param id id of the customer to delete.
	 * @throws ApplicationException
	 */
	public void removeCustomerByCustomerId(long id) throws ApplicationException {
		if(customerDao.getCustomerByCustomerId(id) == null)  {
			throw new ApplicationException(ErrorType.NOT_FOUND, DateUtils.getCurrentDateAndTime() + 
					" Remove company by id has failed."
					+ "\nAttempt to remove company that doesn't exist.");
		}
		customerDao.removeCustomerCouponsByCustomerId(id);
		customerDao.removeCustomerById(id);
	}

	/**
	 * Validates updated customer and updates if validation succeeds, throws exception if validation fails.
	 * @param customer updated customer version to change current version to.
	 * @throws ApplicationException
	 */
	public void updateCustomer(Customer customer) throws ApplicationException {
		if(customerDao.getCustomerByCustomerId(customer.getCustomerId()) == null) {
			throw new ApplicationException(ErrorType.NOT_FOUND, DateUtils.getCurrentDateAndTime() + 
					"Update customer failed." +
					"\nAttempt to update customer that doesn't exist.");
		}
		Customer customerOld = customerDao.getCustomerByCustomerId(customer.getCustomerId());
		
		if(!customer.getCustomerName().equals(customerOld.getCustomerName())) {
			validateCustomerName(customer.getCustomerName());
		}
		if(!customer.getCustomerPassword().equals(customerOld.getCustomerPassword())) {
			validateCustomerPassword(customer.getCustomerPassword());
		}
		customerDao.updateCustomer(customer);
	}
	
	/**
	 * Check if login and password match, throws exception if not.
	 * @param customerName name to check.
	 * @param customerPassword password to check. 
	 * @throws ApplicationException.
	 */
	public boolean customerLogin(String customerName, String customerPassword) throws ApplicationException {
		validationUtils.validateNameSpelling(customerPassword);
		validationUtils.validateAbsence(customerPassword);
		validateCustomerPassword(customerPassword);
		return customerDao.customerLogin(customerName, customerPassword);
	}
}