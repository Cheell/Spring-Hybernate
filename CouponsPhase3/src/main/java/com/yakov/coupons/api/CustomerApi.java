package com.yakov.coupons.api;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yakov.coupons.controller.CustomerController;
import com.yakov.coupons.cookies.CookieUtils;
import com.yakov.coupons.entity.Customer;
import com.yakov.coupons.exceptions.ApplicationException;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/Customers")

/**
 * 
 * @author Yakov
 * Customer API
 */
public class CustomerApi {
	@Autowired private CustomerController customerController;
	
	/**
	 * Create new customer by sending data to Controller
	 * @param customer - customer to be created
	 * @throws ApplicationException 
	 */
	/*
	{	
		"customerName" : "Moppar",
		"customerPassword" : "asdkafjhg"
	}
	*/
	//http://localhost:8080/CouponsPhase3/rest/Customers/
	@RequestMapping(method = RequestMethod.POST)
	public Customer createCustomer(HttpServletRequest request, HttpServletResponse response, @RequestBody Customer customer) throws ApplicationException {
		customerController.createCustomer(customer);
		return customer;
	}

	/**
	 * Search DB for customer with specific id
	 * @param id to search for
	 * @return customer object that was found
	 * @throws ApplicationException if nothing was found
	 */
	@RequestMapping(value ="/{id}/byId", method = RequestMethod.GET)
	//http://localhost:8080/CouponsPhase3/rest/Customers/byId
	public Customer getCustomerByCustomerId(@PathVariable("id") long id)throws ApplicationException {
		return customerController.getCustomerByCustomerId(id);
	}

	/**
	 * Search DB for customer with specific name
	 * @param name to search for
	 * @return customer object that was found
	 * @throws ApplicationException if nothing was found
	 */
	@RequestMapping(value ="/{name}/byName", method = RequestMethod.GET)
	//http://localhost:8080/CouponsPhase3/rest/Customers/byName
	public Customer getCustomerByCustomerName(@PathVariable("name") String name)throws ApplicationException {
		return customerController.getCustomerByCustomerName(name);
	}
	
	
	/**
	 * Requests data of all customers from DB
	 * @return all customers in DB
	 * @throws ApplicationException
	 */
	@RequestMapping(method = RequestMethod.GET)
	//http://localhost:8080/CouponsPhase3/rest/Customers/
	public List<Customer> getAllCustomers() throws ApplicationException {
		return customerController.getAllCustomers();
	}
	
	/**
	 * Removes specific id customer from DB
	 * @param id to remove
	 * @throws ApplicationException
	 */
	@RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
	//http://localhost:8080/CouponsPhase3/rest/Customers/
	public void removeCustomerByCustomerId(@PathVariable("id") long id)throws ApplicationException {
		customerController.removeCustomerByCustomerId(id);
	}

	/**
	 * Updates specific customer using customer id to search
	 * @param customer to update
	 * @throws ApplicationException
	 */
	@RequestMapping(method = RequestMethod.PUT)
	//http://localhost:8080/CouponsPhase3/rest/Customers/
	public Customer updateCustomer(@RequestBody Customer customer, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		customerController.updateCustomer(customer);
		HttpSession session = request.getSession(false);
		Cookie[] cookies = request.getCookies();
		if (session!=null && cookies != null) {
			for(Cookie cookie: cookies) {
				if(cookie.getName().equals(CookieUtils.type) && cookie.getValue().equals(CookieUtils.customerType)) {
					CookieUtils cl = new CookieUtils(customer);
					request.getSession();
					response = cl.addCookies(response);
				}
			}
		}		
		response.setStatus(200);
		return customer;
	}
}
