package com.yakov.coupons.cookies;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.yakov.coupons.entity.Company;
import com.yakov.coupons.entity.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Util class used for Cookies.
 * @author Yakov
 *
 */
public class CookieUtils{
	

	private List<Cookie> cookies = new ArrayList<>();
	
	public final static String customerType = "customer";
	public final static String companyType = "company";

	public final static String id = "id";
	public final static String password = "password";
	public final static String name = "name";
	public final static String type = "type";
	public final static String email = "email";
	

	/**
	 * Constructor of cookies for Customer object.
	 * @param customer Customer to get cookies from.
	 */
	public CookieUtils(Customer customer) {
		this.cookies.add(new Cookie(type, customerType));
		this.cookies.add(new Cookie(id, Long.toString(customer.getCustomerId())));		
		this.cookies.add(new Cookie(password, customer.getCustomerPassword()));
		this.cookies.add(new Cookie(name, customer.getCustomerName()));
	}

	/**
	 * Constructor of cookies for Company object.
	 * @param company Company to get cookies from.
	 */
	public CookieUtils(Company company) {
		this.cookies.add(new Cookie(type, companyType));
		this.cookies.add(new Cookie(id, Long.toString(company.getCompanyId())));
		this.cookies.add(new Cookie(password, company.getCompanyPassword()));
		this.cookies.add(new Cookie(name, company.getCompanyName()));
		this.cookies.add(new Cookie(email, company.getCompanyEmail()));
	}
	
	public List<Cookie> getCookies() {
		return cookies;
	}

	public void setCookies(List<Cookie> cookies) {
		this.cookies = cookies;
	}

	/**
	 * Adds cookies to user
	 * @param response HttpServletResponse used to actually add cookies.
	 * @return HttpServletResponse sending updated cookies to the client.
	 */
	public HttpServletResponse addCookies(HttpServletResponse response) {
		for(Cookie cookie : cookies)	{
			cookie.setMaxAge(60*60);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		return response;
	}
}
