package com.yakov.coupons.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.yakov.coupons.entity.Coupon;
import com.yakov.coupons.entity.Customer;
import com.yakov.coupons.entity.Purchase;
import com.yakov.coupons.dao.CompanyDao;
import com.yakov.coupons.dao.CouponDao;
import com.yakov.coupons.dao.CustomerDao;
import com.yakov.coupons.exceptions.ApplicationException;
import com.yakov.coupons.enums.ErrorType;
import com.yakov.coupons.enums.CouponTypes;
import com.yakov.coupons.utils.DateUtils; 
import com.yakov.coupons.utils.validationUtils;

/**
 * Logics for Coupon object.
 * @author Yakov.
 *
 */
@Controller
public class CouponController {
    @Autowired private CouponDao couponDao;
    @Autowired private CustomerDao customerDao;
	
	/**
	 * Validates coupon and either creates it in DB or throws exception if validation fails.
	 * @param coupon - coupon to validate
	 * @throws ApplicationException
	 */
	public void createCoupon(Coupon coupon) throws ApplicationException{
		System.out.println(coupon);
		validateDates(coupon.getCouponStartDate(), coupon.getCouponEndDate());
		validateCouponTitle(coupon.getCouponTitle());
		validateCouponAmmount(coupon.getCouponAmmount());
		validateCouponPrice(coupon.getCouponPrice());
		validateCouponImage(coupon.getCouponImage());
		this.couponDao.createCoupon(coupon);
	}

	/**
	 * Validates title of the coupon.
	 * @param title title to validate
	 * @throws ApplicationException
	 */
	private void validateCouponTitle(String title) throws ApplicationException{
		validationUtils.validateAbsence(title);
		validationUtils.validateNameSpelling(title);
		validateTitleDoNotExistYet(title);
	}

	/**
	 * Validates start and end date of the coupon, throws exception if validation fails.
	 * @param start start date to validate.
	 * @param end end date to validate.
	 * @throws ApplicationException
	 */
	private void validateDates(String start, String end) throws ApplicationException {
		validationUtils.validateAbsence(start);
		validationUtils.validateDateSpelling(start);
		validationUtils.validateAbsence(end);
		validationUtils.validateDateSpelling(end);		
		validationUtils.endOlderThenStart(end, start);
	}
		
	/**
	 * Validates uniqueness of coupon title.
	 * @param title title to validate
	 * @throws ApplicationException
	 */
	private void validateTitleDoNotExistYet(String title) throws ApplicationException {
		if(couponDao.getCouponByCouponTitle(title) != null) {
			throw new ApplicationException(ErrorType.NAME_ALREADY_EXISTS, DateUtils.getCurrentDateAndTime() + 
					"\nAttempt to set coupon title using a name that already exists.");
		}
	}	

	/**
	 * Validates if coupon amount is between 0 and 1million if no throws exception.
	 * @param amount amount to validate
	 * @throws ApplicationException
	 */
	private void validateCouponAmmount(int amount) throws ApplicationException {
		if (amount < 0) {
			throw new ApplicationException(ErrorType.INCORRECT_VALUE, DateUtils.getCurrentDateAndTime() + 
					"\nAttempt to set coupon ammount to lesser then zero."); 
		}
		if (amount > 1000000) {
			throw new ApplicationException(ErrorType.INCORRECT_VALUE, DateUtils.getCurrentDateAndTime() + 
					"\nAttempt to set coupon ammount to bigger then 1 million");
		}
	}
	
	/**
	 * Validates if coupon price is between 0 and 1million if no throws exception.
	 * @param price price to validate.
	 * @throws ApplicationException
	 */
	private void validateCouponPrice(double price) throws ApplicationException {
		if (price < 0) {
			throw new ApplicationException(ErrorType.INCORRECT_VALUE, DateUtils.getCurrentDateAndTime() +
					"\nAttempt to set coupon price to lesser then zero.");
		}
		if  (price > 1000000) {
			throw new ApplicationException(ErrorType.INCORRECT_VALUE, DateUtils.getCurrentDateAndTime() + 
					"\nAttempt to set coupon price to bigeer then 1 million.");
		}
	}
	
	/**
	 * Validates link to coupon image, it should end with one of allowed image formats, 
	 * else throws exception
	 * @param image
	 * @throws ApplicationException
	 */
	private void validateCouponImage(String image) throws ApplicationException {
		String extension =".*\\.img$|.*\\.jpg$|.*\\.png$|.*\\.gif$|.*\\.bmp$|.*\\.webp$"; //expression with allowed formats.
		if (!image.matches(extension)) {
			throw new ApplicationException(ErrorType.INCORRECT_NAME_SPELLING, DateUtils.getCurrentDateAndTime() + 
					"\nAttempt to use image with extension that is not allowed.");
		}
	}
	
	/**
	 * Checks if company id exists in DB if not throws exception.
	 * @param companyId id to check.
	 * @throws ApplicationException
	 */
	private void validateCompanyId(long companyId) throws ApplicationException {
		CompanyDao companyDao = new CompanyDao();
		if(companyDao.getCompanyByCompanyId(companyId) == null) {
			throw new ApplicationException(ErrorType.NOT_FOUND, DateUtils.getCurrentDateAndTime() + 
					"\nAttempt to use company that doesnt exist.");
		}
	}
	

	
	/**
	 * Searches for coupon by id, throws exception if no coupon found.
	 * @param id id to search for
	 * @return coupon that was found
	 * @throws ApplicationException
	 */
	public Coupon getCouponByCouponId(long id) throws ApplicationException {
		Coupon coupon = couponDao.getCouponByCouponId(id);
		return coupon;
	}
	
	/**
	 * Searches for coupon by title, throws exception if no coupon found.
	 * @param name title to search for
	 * @return coupon that was found
	 * @throws ApplicationException
	 */	
	public Coupon getCouponByCouponTitle(String name) throws ApplicationException {
		Coupon coupon = couponDao.getCouponByCouponTitle(name);
		return coupon;
	}
	
	
	/**
	 * Gets List of all coupons that exist in DB.
	 * @return list of coupons that were found.
	 * @throws ApplicationException
	 */
	public List<Coupon> getAllCoupons() throws ApplicationException {
		return couponDao.getAllcoupons();
	}
	
	
	/**
	 * Deletes coupon by id from DB and all its purchases or throws exception if no coupon found by provided id.
	 * @param id id of the company to delete.
	 * @throws ApplicationException
	 */
	public void removeCouponByCouponId(long id) throws ApplicationException {
		if(couponDao.getCouponByCouponId(id) == null) {
			throw new ApplicationException(ErrorType.NOT_FOUND, DateUtils.getCurrentDateAndTime() + 
					" Remove coupon by id has failed."
					+ "\nTry to remove coupon that doesn't exist.");
		}
		couponDao.removeCouponsFromCustomerCouponsByCouponId(id);
		couponDao.removeCouponByCouponId(id);
	}
	
	/**
	 * Deletes coupons with provided company id from DB and all their purchases or throws exception if no coupon found by provided company id.
	 * @param id id of the company to delete.
	 * @throws ApplicationException
	 */
	public void removeCouponsByCompanyId(long id) throws ApplicationException {
		List<Coupon> couponsList = couponDao.getCouponsByCompanyId(id);
		if(couponsList.isEmpty()) {
			throw new ApplicationException(ErrorType.NOT_FOUND, DateUtils.getCurrentDateAndTime() + 
					" Remove coupon by company id failed." +
					"\nTry to remove coupons of the company that doesn't exist.");
		}
		for(Coupon i : couponsList) {
			couponDao.removeCouponsFromCustomerCouponsByCouponId(i.getCouponId());			
		}
		couponDao.removeCouponsByCompanyId(id);
	}
	/**
	 * Validates updated coupon and updates if validation succeeds, throws exception if validation fails.
	 * @param coupon updated coupon version to change current version.
	 * @throws ApplicationException
	 */
	public void updateCoupon(Coupon coupon) throws ApplicationException {
		if(couponDao.getCouponByCouponId(coupon.getCouponId()) == null) {
			throw new ApplicationException(ErrorType.NOT_FOUND, DateUtils.getCurrentDateAndTime() + 
					" update coupon by company id failed." +
					"\nAttempt to update coupon that doesn't exist.");
		}
		Coupon coponOld = couponDao.getCouponByCouponId(coupon.getCouponId());
		
		if(!coponOld.getCouponTitle().equals(coupon.getCouponTitle())) {
			validateCouponTitle(coupon.getCouponTitle());
		}
		validateDates(coupon.getCouponStartDate(), coupon.getCouponEndDate());			
		validateCouponAmmount(coupon.getCouponAmmount());			
		validateCouponPrice(coupon.getCouponPrice());
		validateCouponImage(coupon.getCouponImage());
		validateCompanyId(coupon.getCompanyId());
		couponDao.updateCoupon(coupon);
	}
	
	/**
	 * Search for coupons of particular type.
	 * @param type type to search for.
	 * @return list of coupons that were found.
	 * @throws ApplicationException
	 */
	public List<Coupon> getCouponsByType(CouponTypes type) throws ApplicationException {
		return couponDao.getCouponsByType(type);
	}
	
	/**
	 * Search for coupons with particular company id.
	 * @param companyId id to search for.
	 * @return list of coupons that were found.
	 * @throws ApplicationException
	 */
	public List<Coupon> getCouponsByCompanyId(long companyId) throws ApplicationException {
		return couponDao.getCouponsByCompanyId(companyId);
	}

	/**
	 * Search for coupon purchased by customer with particular id.
	 * @param customerId id to search for.
	 * @return list of purchases that were found.
	 * @throws ApplicationException
	 */
	public List<Purchase> getCouponPurchasesByCustomerId(long id) throws ApplicationException {
		Customer customer = customerDao.getCustomerByCustomerId(id);
		if(customer == null) {
			throw new ApplicationException(ErrorType.NOT_FOUND, DateUtils.getCurrentDateAndTime() + 
				"get coupons by customer id failed." + 
				"\nCustomer with such an id doesnt exist.");
		}
		return couponDao.getCouponPurchasesByCustomerId(id);
	}
	
	/**
	 * Searches for coupon in provided price range, check if range is valid, throws exception if validation fails.
	 * @param couponPriceFrom price to start search from.
	 * @param couponPriceTo price to search to.
	 * @return list of coupons that were found.
	 * @throws ApplicationException
	 */
	public List<Coupon> getCouponsByPrice(double couponPriceFrom, double couponPriceTo) throws ApplicationException {
		if (couponPriceFrom > 1000000) {
			throw new ApplicationException(ErrorType.INCORRECT_VALUE, DateUtils.getCurrentDateAndTime() + 
					"get coupons by price failed." + 
					"\nStart price is too big.");
		}
		if (couponPriceTo < 0) {
			throw new ApplicationException(ErrorType.INCORRECT_VALUE, DateUtils.getCurrentDateAndTime() + 
					"get coupons by price failed." + 
					"\nEnd price is too small.");
		}
		if(couponPriceFrom > couponPriceTo) {
			throw new ApplicationException(ErrorType.INCORRECT_VALUE, DateUtils.getCurrentDateAndTime() + 
					"get coupons by price failed." + 
					"\nStart price is bigger than End price.");
		}		
		return couponDao.getCouponsByPrice(couponPriceFrom, couponPriceTo);		
	}
	
	/**
	 * Adds new coupon purchase to DB and updates coupon amount.
	 * Validates the purchase by checking the existence of coupon and customer(by id),
	 * And available amount of coupon left.
	 * @param couponId id of the coupon to buy.
	 * @param customerId of the buyer.
	 * @param amountPurchased amount of coupons to be purchased.
	 * @throws ApplicationException
	 */
	public void makePurchase(Purchase couponPurchase) throws ApplicationException {
		//coupon id check
		Coupon coupon = couponDao.getCouponByCouponId(couponPurchase.getCouponId());
		if(coupon == null) {
			throw new ApplicationException(ErrorType.NOT_FOUND, DateUtils.getCurrentDateAndTime() + 
					"Purchase coupon failed." + 
					"\nAttempt to purchase coupon that doesnt exist.");
		}
		
		//customer id check
		if(customerDao.getCustomerByCustomerId(couponPurchase.getCustomerId()) == null) {
			throw new ApplicationException(ErrorType.NOT_FOUND, DateUtils.getCurrentDateAndTime() + 
					"Purchase coupon failed." + 
					"\nAttempt to purchase coupon by customer that doesnt exist.");
		}
		
		//coupon amount check
		int currentCouponAmount = couponDao.getCouponByCouponId(couponPurchase.getCouponId()).getCouponAmmount();
		int newCouponAmount = currentCouponAmount - couponPurchase.getAmmount();
		if( newCouponAmount < 0) {
			throw new ApplicationException(ErrorType.INCORRECT_VALUE, DateUtils.getCurrentDateAndTime() + 
					"Purchase coupon failed." + 
					"\nAttempt to purchase more coupons than we have in DB.");
		}
		coupon.setCouponAmmount(newCouponAmount);
		couponDao.addPurchase(couponPurchase);
		couponDao.updateCoupon(coupon);
	}

}