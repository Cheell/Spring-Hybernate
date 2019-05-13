package com.yakov.coupons.api;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yakov.coupons.entity.Coupon;
import com.yakov.coupons.entity.Purchase;
import com.yakov.coupons.controller.CouponController;
import com.yakov.coupons.exceptions.ApplicationException;
import com.yakov.coupons.enums.CouponTypes;

/**
 * Coupon API
 * @author Yakov
 *
 */
@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/Coupons")
public class CouponApi {
	@Autowired private CouponController couponController;
	/**
	 * Creates new Coupon object
	 * @param coupon Object to create
	 * @throws ApplicationException
	 */
	//http://localhost:8080/CouponsPhase3/rest/Coupons/
	/*
 	{
	"companyId": "1",
	"couponAmmount": "50",
	"couponEndDate": "2033-02-01",
	"couponImage": "No pic.img",
	"couponMessage": "Wooot???",
	"couponPrice": "11.2",
	"couponStartDate": "2010-01-01",
	"couponTitle": "Ya",
	"couponType": "Castle"
	}
	*/
	@RequestMapping(method = RequestMethod.POST)
	public Coupon createCoupon(@RequestBody Coupon coupon) throws ApplicationException{
		 couponController.createCoupon(coupon);
		 return coupon;
	}
	
	/**
	 * Search for Coupon object by specific id
	 * @param id
	 * @return Coupon object that was found
	 * @throws ApplicationException
	 */

	//http://localhost:8080/CouponsPhase3/rest/Coupons/1/byId
	@RequestMapping(value ="/{id}/byId", method = RequestMethod.GET)
	public Coupon getCouponByCouponId(@PathVariable("id")long id) throws ApplicationException {
		return couponController.getCouponByCouponId(id);
	}

	/**
	 * Search for Coupon Object with specific title
	 * @param name title String to search for 
	 * @return Coupon object that was found
	 * @throws ApplicationException if nothing was found
	 */
	//http://localhost:8080/CouponsPhase3/rest/Coupons/Ya/byTitle
	@RequestMapping(value ="/{title}/byTitle", method = RequestMethod.GET)
	public Coupon getCouponByCouponTitle(@PathVariable("title") String name) throws ApplicationException {
		return couponController.getCouponByCouponTitle(name);
	}

	/**
	 * Request for all Coupon objects in DB
	 * @return List of Coupon objects
	 * @throws ApplicationException
	 */
	//http://localhost:8080/CouponsPhase3/rest/Coupons/
	@RequestMapping(method = RequestMethod.GET)
	public List<Coupon> getAllCoupons() throws ApplicationException {
		return couponController.getAllCoupons();
	}

	/**
	 * Removes Coupon object by specific id
	 * @param id id to remove
	 * @throws ApplicationException
	 */
	//http://localhost:8080/CouponsPhase3/rest/Coupons/1/byId
	@RequestMapping(value ="/{id}/byId", method = RequestMethod.DELETE)
	public void removeCouponByCouponId(@PathVariable("id") long id) throws ApplicationException {
		couponController.removeCouponByCouponId(id);
	}

	/**
	 * Removes all Coupons objects with specific Company id
	 * @param id company id to remove
	 * @throws ApplicationException
	 */
	@RequestMapping(value ="/byCompanyId", method = RequestMethod.DELETE)
	//http://localhost:8080/CouponsPhase3/rest/Coupons/byCompanyId?id=1
	public void removeCouponsByCompanyId(@PathVariable("id") long id) throws ApplicationException {
		couponController.removeCouponsByCompanyId(id);
	}
	
	/**
	 * Updates Coupon object (by id) to new version
	 * @param coupon Coupon object to update
	 * @throws ApplicationException
	 */
	//http://localhost:8080/CouponsPhase3/rest/Coupons/
	@RequestMapping(method = RequestMethod.PUT)
	public Coupon updateCoupon(Coupon coupon) throws ApplicationException {
		couponController.updateCoupon(coupon);
		return coupon;
	}
	
	/**
	 * Search for Coupon objects of specific type
	 * @param type to search for
	 * @return List of Coupon Objects
	 * @throws ApplicationException
	 */	
	//http://localhost:8080/CouponsPhase3/rest/Coupons/byType?type=Castle
	@RequestMapping(value ="/byType", method = RequestMethod.GET)
	public List<Coupon> getCouponsByType(@RequestParam("type") String type) throws ApplicationException {
		return couponController.getCouponsByType(CouponTypes.valueOf(type));
	}

	/**
	 * Search for Coupon objects with specific Company Id
	 * @param type to search for
	 * @return List of Coupon Objects
	 * @throws ApplicationException
	 */
	//http://localhost:8080/CouponsPhase3/rest/Coupons/byCompId?id=35
	@RequestMapping(value ="/byCompId", method = RequestMethod.GET)
	public List<Coupon> getCouponsByCompanyId(@RequestParam("id")long id) throws ApplicationException {
		System.out.println(id);
		return couponController.getCouponsByCompanyId(id);
	}


	/**
	 * Search for purchases of specific customer by id
	 * @param customerId customer id to search for
	 * @return List of CouponPurchase objects
	 * @throws ApplicationException
	 */
	//http://localhost:8080/CouponsPhase3/rest/Coupons/purchasesByCustomerId?id=1
	@RequestMapping(value ="/purchasesByCustomerId", method = RequestMethod.GET)
	public List<Purchase> getCouponPurchasesByCustomerId(@RequestParam("id")long customerId) throws ApplicationException {
		return couponController.getCouponPurchasesByCustomerId(customerId);
	}

	/**
	 * Search for Coupon objects with price between two "borders"
	 * @param couponPriceFrom lower border
	 * @param couponPriceTo upper border
	 * @return List of Coupon objects
	 * @throws ApplicationException
	 */
	//http://localhost:8080/CouponsPhase3/rest/Coupons/byPrice?from=1&to=100
	@RequestMapping(value ="/byPrice", method = RequestMethod.GET)
	public List<Coupon> getCouponsByPrice(@RequestParam("from") double couponPriceFrom, @RequestParam("to") double couponPriceTo) throws ApplicationException {
		return couponController.getCouponsByPrice(couponPriceFrom, couponPriceTo);
	}

	/**
	 * Add CouponPurchase object to DB
	 * @param purchase CouponPurchase to add
	 * @throws ApplicationException
	 */
	/*
	{
		"customerId" : "1",
		"couponId" : "2",
		"ammount" : "10"
	}
	*/
	//http://localhost:8080/CouponsPhase3/rest/Coupons/purchase
	@RequestMapping(value ="/purchase", method = RequestMethod.POST)
	public void purchaseCouponAmmount(@RequestBody Purchase purchase) throws ApplicationException {
		couponController.makePurchase(purchase);
	}
	
}