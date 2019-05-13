package com.yakov.coupons.api;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yakov.coupons.beans.User;
import com.yakov.coupons.controller.CompanyController;
import com.yakov.coupons.controller.CustomerController;
import com.yakov.coupons.cookies.CookieUtils;
import com.yakov.coupons.exceptions.ApplicationException;


/**
 * Login Api 
 * @author Yakov
 *
 */
@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/Login")

public class LoginApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired 	private CompanyController companyController;
	@Autowired 	private CustomerController customerController;

	/*
	  {
	  	"userName":"Moppar",
	  	"userPassword":"asdkafjhg"
	  }
	 */
	
	/**
	 * Login for Customer
	 * @param user UserLogin object with data required for login
	 * @param request provided HttpServletRequest object
	 * @param response provided HttpServletResponse object
	 * @return Response status
	 * @throws ApplicationException
	 */
	//http://localhost:8080/CouponsPhase3/rest/Login/asCustomer
	@RequestMapping(value ="/asCustomer", method = RequestMethod.POST)
	public User customerLogin(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		customerController.customerLogin(user.getUserName(), user.getUserPassword()); 
		CookieUtils cl = new CookieUtils(customerController.getCustomerByCustomerName(user.getUserName()));
		request.getSession(true);
		response = cl.addCookies(response);
		response.setStatus(200);
		return user;
	}
	
	/**
	 * Logout for any kind of user
	 * @param user UserLogin object with data required for login
	 * @param request provided HttpServletRequest object
	 * @param response provided HttpServletResponse object
	 * @return Response status
	 * @throws ApplicationException
	 */
	/* 
	{
	  	"userName":"Moppar",
	  	"userPassword":"asdkafjhg"
	}
	*/
	//http://localhost:8080/CouponsPhase3/rest/Login/asCompany
	@RequestMapping(value ="/asCompany", method = RequestMethod.POST)
	public User companyLogin(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		companyController.companyLogin(user.getUserName(), user.getUserPassword());
		CookieUtils cl = new CookieUtils(companyController.getCompanyByCompanyName(user.getUserName()));
		request.getSession(true);
		response = cl.addCookies(response);
		response.setStatus(200);
		return user;
	}

	
	/**
	* Logout for any kind of user
	* deletes users session
	* @return Response status
	* @throws ApplicationException
	*/
	//I don't want to create another class to put Logout there.
	// Maybe putting Logout in Login class is not the best thing to do, but next time ill just stick to auth class.
	@RequestMapping(value ="/Logout", method = RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		request.getSession(false).invalidate();
		response.setStatus(200);
	}
}
