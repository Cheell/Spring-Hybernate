package com.yakov.coupons.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yakov.coupons.entity.Coupon;
import com.yakov.coupons.entity.Purchase;
import com.yakov.coupons.exceptions.ApplicationException;
import com.yakov.coupons.enums.*;
import com.yakov.coupons.utils.DateUtils;

/**
 * 
 * @author Yakov
 *	Dao that works with Coupons.
 */

@Repository
public class CouponDao{
	@PersistenceContext(unitName="demo")
	private EntityManager entityManager;
	/**
	 * Adds new coupon to the coupon table.
	 * @param coupon to add to the table.
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void createCoupon(Coupon coupon) throws ApplicationException {
		try {
			entityManager.persist(coupon);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, creatCoupon(); FAILED");
		}
	}

	/**
	 * Finds a coupon by coupon ID.
	 * @param couponId Id to look for the coupon.
	 * @return coupon.
	 * @throws ApplicationException 
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Coupon getCouponByCouponId(long couponId) throws ApplicationException {
		try {
			return entityManager.find(Coupon.class, couponId);
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CouponDao, getCouponByCouponId(long couponId); FAILED");
		}

	}

	/**
	 * Finds a coupon by coupon Title.
	 * @param coupon Title to look for the coupon.
	 * @return coupon.
	 * @throws ApplicationException 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public Coupon getCouponByCouponTitle(String couponTitle) throws ApplicationException {
		try {
			Query query = entityManager.createQuery("SELECT coupon FROM Coupon as coupon WHERE couponTitle =:couponTitleObj");
			query.setParameter("couponTitleObj", couponTitle);
			return (Coupon)query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, getCouponByCouponTitle(String couponTitle); FAILED");
		}
	}
		
	/**
	 * Extracts data of all coupons from Data Base.
	 * @return List of all coupons.
	 * @throws ApplicationException 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<Coupon> getAllcoupons() throws ApplicationException{
		
		List<Coupon> couponsList = new ArrayList<>();
		try {
			Query getQuery = entityManager.createQuery("SELECT coupon FROM Coupon as coupon");
			couponsList = getQuery.getResultList();
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, getAllcoupons(); FAILED");
		}
		return couponsList;
	}

	/**
	 * Deletes all coupons with specific company id from coupon table.
	 * @param companyId id of the company to search and delete.
	 * @throws ApplicationException.
	 */	
	@Transactional(propagation=Propagation.REQUIRED)
	public void removeCouponsByCompanyId(long companyId) throws ApplicationException{
		try {
			  Query query = entityManager.createQuery("DELETE FROM Coupon coupon WHERE companyId = :companyIdObj");
			  query.setParameter("companyIdObj", companyId).executeUpdate();
			
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CouponDao, removeCouponsByCompanyId(long companyId); FAILED");
		}
	}

	/**
	 * Deletes coupons with specific id from customer_coupons table.
	 * @param id coupon id to search for and delete.
	 * @throws ApplicationException.
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void removeCouponsFromCustomerCouponsByCouponId(long id) throws ApplicationException {
		try {
			  Query query = entityManager.createQuery("DELETE FROM Purchase purchase WHERE couponId = :coupIdObj");
			  query.setParameter("coupIdObj", id).executeUpdate();
		}

		catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CouponDao, removeCouponsFromCustomerCouponsByCouponId; FAILED");
		}
	}
	
	
	/**
	 * Removes coupon with certain ID from coupon table. 
	 * @param couponId coupon id to remove.
	 * @throws ApplicationException 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void removeCouponByCouponId(long couponId) throws ApplicationException {
		try {
			Coupon coupon = getCouponByCouponId(couponId);
			entityManager.remove(coupon);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CouponDao, removeCouponByCouponId; FAILED");
		}
	}
	
	/**
	 * Updates data of certain coupon using coupon id as a search parameter.
	 * @param coupon - coupon thats we update, and get id from.
	 * @throws ApplicationException 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateCoupon(Coupon coupon) throws ApplicationException {
		try {
			entityManager.merge(coupon);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CouponDao, updateCoupon(Coupon coupon); FAILED");
		}
	}
	
	/**
	 * Finds all coupons with specific type.
	 * @param couponType - type to search for.
	 * @return List of coupons with specific type.
	 * @throws ApplicationException 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<Coupon> getCouponsByType(CouponTypes couponType) throws ApplicationException{
		List<Coupon> couponByTypeList = new ArrayList<>();
		try {
			Query query = entityManager.createQuery("SELECT coupon FROM Coupon as coupon WHERE couponType =:couponTypeObj");
			query.setParameter("couponTypeObj", couponType);
			couponByTypeList = query.getResultList();
			return couponByTypeList;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() +
					"Error in CouponDao, getCouponByType(CouponTypes couponType); FAILED");			
		}
	}

	/**
	 * Searches for all coupons with specific company ID.
	 * @param companyId id to search for.
	 * @return List of coupons that were found.
	 * @throws ApplicationException. 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<Coupon> getCouponsByCompanyId(long companyId) throws ApplicationException{		
		List<Coupon> couponList = new ArrayList<>(); 
		try {
			Query query = entityManager.createQuery("SELECT coupon FROM Coupon as coupon WHERE companyId =:companyIdObj");
			query.setParameter("companyIdObj", companyId);
			couponList = query.getResultList();
			return couponList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CouponDao, getCouponByCompanyId(long companyId); FAILED");
		}
	}	

	/**
	 * Searches for all purchases by certain customer.
	 * @param customerId id to search for.
	 * @return List of coupon purchases that were found.
	 * @throws ApplicationException 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<Purchase> getCouponPurchasesByCustomerId(long id) throws ApplicationException{
		
		List<Purchase> purchases = new ArrayList<>(); 
		
		try {
			Query query = entityManager.createQuery("SELECT purchase FROM Purchase as purchase WHERE customerId =:customerIdObj");
			query.setParameter("customerIdObj", id);
			purchases = query.getResultList();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CouponDao, getCouponsByCustomer FAILED");
		}
		return purchases;
	}
	
	
	/**
	 * Adds a new row to the customer_coupon table.
	 * @param couponId id if the coupon that was bought.
	 * @param customerId id of the buyer.
	 * @param amount amount of coupons that were bought.
	 * @throws ApplicationException
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void addPurchase(Purchase purchase) throws ApplicationException{
		try {
			entityManager.persist(purchase);
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CouponDao, addPurchase; FAILED");
		} 
	}


	/**
	 * Finds all coupons with price that is between 2 prices set by parameters.
	 * @param couponPriceFrom first price to start the search from - the lesser one of two.
	 * @param couponPriceTo second price to search up to that pricer - the bigger one.
	 * @return list of coupons which prices are in the range determined by parameters of the function.
	 * @throws ApplicationException.
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<Coupon> getCouponsByPrice(double couponPriceFrom, double couponPriceTo) throws ApplicationException {
		List<Coupon> couponsList = new ArrayList<>();
		
		try {
			Query query = entityManager.createQuery("SELECT coupon FROM Coupon as coupon WHERE couponPrice BETWEEN :couponPriceFrom AND :couponPriceTo");
			query.setParameter("couponPriceFrom", couponPriceFrom);
			query.setParameter("couponPriceTo", couponPriceTo);
			couponsList = query.getResultList();
			return couponsList;
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CouponDao, getCouponsByPrice; FAILED");
		}
	}
	
	/**
	 * Finds Ids of all coupons with end date smaller than parameter (in other words end date that is older than the parameter).
	 * @param date to search for.
	 * @return list of ids.
	 * @throws ApplicationException.
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<Long> getCouponIdsWhichEndDateIsOlderThanDate(String date) throws ApplicationException{		
		List<Long> couponIds = new ArrayList<>();
		try {
			Query query = entityManager.createQuery("SELECT coupon FROM Coupon as coupon WHERE date <:couponEndDateObj");
			query.setParameter("couponEndDateObj", date);
			couponIds = query.getResultList();
			return couponIds;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CouponDao, getCouponsIdByEndDate; FAILED");
		}
	}	
}