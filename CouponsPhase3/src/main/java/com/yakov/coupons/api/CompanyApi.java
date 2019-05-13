package com.yakov.coupons.api;
/**
 * API for Company object
 */
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

import com.yakov.coupons.controller.CompanyController;
import com.yakov.coupons.cookies.CookieUtils;
import com.yakov.coupons.entity.Company;
import com.yakov.coupons.exceptions.ApplicationException;



/**
 * 
 * @author Yakov
 * Company API
 */
@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/Companies")
public class CompanyApi {
	@Autowired
	private CompanyController companyController;

	/**
	 * Creates new Company object
	 * @param company company to create
	 * @throws ApplicationException
	 */
	/*
	{	
		"companyName" : "Moppar",
		"companyPassword" : "asdkafjhg",
		"companyEmail" : "addw@dajds.hgj"
	}
	*/
	//http://localhost:8080/CouponsPhase3/rest/Companies/
	@RequestMapping(method = RequestMethod.POST)
	public Company createCompany(HttpServletRequest request, HttpServletResponse response, @RequestBody Company company) throws ApplicationException{
		companyController.createCompany(company);
		return company;
	}
	
	/**
	 * Search for Company with specific id provided
	 * @param id id to search for
	 * @return Company object that was found
	 * @throws ApplicationException if nothing was found
	 */
	
	//http://localhost:8080/CouponsPhase3/rest/Companies/1/byId
	@RequestMapping(value ="/{id}/byId", method = RequestMethod.GET)
	public Company getCompanyByCompanyId(@PathVariable("id") long id) throws ApplicationException {
		return companyController.getCompanyByCompanyId(id);
	}
	
	/**
	 * Search for Company with specific name
	 * @param name name to search for
	 * @return Company object that was found
	 * @throws ApplicationException if nothing was found
	 */
	//http://localhost:8080/CouponsPhase3/rest/Companies/!/byName
	@RequestMapping(value ="/{name}/byName", method = RequestMethod.GET)
	public Company getCompanyByCompanyName(@PathVariable("name") String name) throws ApplicationException {
		return companyController.getCompanyByCompanyName(name);
	}

	/**
	 * Search Company with specific email
	 * @param email email string to search for Company object
	 * @return Company object that was found
	 * @throws ApplicationException if nothing was found
	 */
	//http://localhost:8080/CouponsPhase3/rest/Companies/si@pei.com/byEmail
	@RequestMapping(value ="/{email}/byEmail", method = RequestMethod.GET)
	public Company getCompanyByCompanyEmail(@PathVariable("email") String email) throws ApplicationException {
		return companyController.getCompanyByCompanyEmail(email);
	}
	
	/**
	 * For security reason we can't use getCompanyById,
	 * cause this way any customer may get information about any other companies,
	 * and we don't want that to happen!
	 * So this is a secure version of getCompany. 
	 * @param id to search for
	 * @return customer object that was found
	 * @throws ApplicationException if nothing was found
	 */
	@RequestMapping(value ="/{id}/nameById", method = RequestMethod.GET)
	//http://localhost:8080/CouponsPhase3/rest/Companies/31/nameById
	public Company getCompanyNameByCompanyId(@PathVariable("id") long id)throws ApplicationException {
		Company company = companyController.getCompanyByCompanyId(id);
		company.setCompanyEmail(null);
		company.setCompanyPassword(null);
		return company;
	}

	
	/**
	 * Requests all Company objects from DB
	 * @return List of Company objects
	 * @throws ApplicationException
	 */
	//http://localhost:8080/CouponsPhase3/rest/Companies/
	@RequestMapping(method = RequestMethod.GET)
	public List<Company> getAllCompanies() throws ApplicationException {
		return companyController.getAllCompanies();
	}
	
	/**
	 * Removes Company object with specific id from DB.
	 * @param id to remove Company
	 * @throws ApplicationException 
	 */
	//http://localhost:8080/CouponsPhase3/rest/Companies/
	@RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
	public void removeCompanyByCompanyId(@PathVariable("id") long id) throws ApplicationException {
		companyController.removeCompanyByCompanyId(id);
	}
	
	/**
	 * Updates Company (by id) to new version, that was provided
	 * Checks if our current cookie show that user is logged in as company
	 * Updates cookies
	 * @param company to update
	 * @throws ApplicationException
	 */
	@RequestMapping(method = RequestMethod.PUT)
	//http://localhost:8080/CouponsPhase3/rest/Companies/
	public Company updateCompany(@RequestBody Company company, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		companyController.updateCompany(company);
		HttpSession session = request.getSession(false);
		Cookie[] cookies = request.getCookies();
		if (session!=null && cookies != null) {
			for(Cookie cookie: cookies) {
				if(cookie.getName().equals(CookieUtils.type) && cookie.getValue().equals(CookieUtils.companyType)) {
					CookieUtils cl = new CookieUtils(company);
					request.getSession();
					response = cl.addCookies(response);
				}
			}
		} 
		return company;
	}
}
