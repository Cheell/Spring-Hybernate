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

import com.yakov.coupons.exceptions.ApplicationException;
import com.yakov.coupons.entity.Customer;
import com.yakov.coupons.enums.ErrorType;
import com.yakov.coupons.utils.DateUtils;

/**
 * 
 * @author Yakov
 *	Dao that works with Customers.
 */
@Repository
public class CustomerDao{
	@PersistenceContext(unitName="demo")
	private EntityManager entityManager;
	/**
	 * Adds new customer to the customer table.
	 * @param customer to add to the table.
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void createCustomer(Customer customer) throws ApplicationException {
		try {
			entityManager.persist(customer);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() 
					+ "Error in CustomerDao, creatCustomer(); FAILED");
		}
	}

	/**
	 * Finds a customer by customer ID.
	 * @param customerId Id to look for the customer.
	 * @throws ApplicationException 
	 * @return customer
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Customer getCustomerByCustomerId(long customerId) throws ApplicationException {
		try {
			return entityManager.find(Customer.class, customerId);
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CustomerDao, getCustomerByCustomerId; FAILED");
		}
	}

	/**
	 * Finds a customer by customer name.
	 * @param customerName - name to look for the customer.
	 * @throws ApplicationException 
	 * @return customer
	 */
	public Customer getCustomerByCustomerName(String customerName) throws ApplicationException {
		try {
			Query query = entityManager.createQuery("SELECT customer FROM Customer as customer WHERE customerName =:customerNameObj");
			query.setParameter("customerNameObj", customerName);
			return (Customer)query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CustomerDao, getCustomerByCustomerName; FAILED");
		}
	}
	
	/**
	 * Extracts data of all customers from Data Base.
	 * @return List of all customers.
	 * @throws ApplicationException 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<Customer> getAllCustomers() throws ApplicationException{		
		List<Customer> customersList = new ArrayList<>();
		try {
			Query getQuery = entityManager.createQuery("SELECT customer FROM Customer As customer");
			customersList = getQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CustomerDao, getAllCustomers(); FAILED");
		}
		return customersList;
	}
	
	/**
	 * Removes customer with certain ID from customer table. 
	 * @param customerId id of the customer that is going to be removed.
	 * @throws ApplicationException 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void removeCustomerById(long customerId) throws ApplicationException {
		try {
			  Query query = entityManager.createQuery("DELETE FROM Customer customer WHERE customerId = :customerIdObj");
			  query.setParameter("customerIdObj", customerId).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CustomerDao, removeCustomerById(long customerId); FAILED");
		}
	}
	
	/**
	 * Removes all coupons bought by customer with certain ID from Data Base. 
	 * (deletes data from customer_coupon table)
	 * @param customerId customer id to search for coupons.
	 * @throws ApplicationException 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void removeCustomerCouponsByCustomerId(long customerId) throws ApplicationException {
		try {
			Query query = entityManager.createQuery("DELETE FROM Purchase purchase WHERE customerId = :customerIdObj");
			query.setParameter("customerIdObj", customerId).executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CustomerDao, removeCustomerCouponsByCustomerId(long customerId); FAILED");
		}
	}
	
	
	
	/**
	 * Updates data of certain customer using customer id as a search parameter.
	 * @param customer thats we update.
	 * @throws ApplicationException 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateCustomer(Customer customer) throws ApplicationException {
		try {
			entityManager.merge(customer);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CustomerDao, updateCustomer(Customer customer); FAILED");
		}
	}	
		
	/**
	 * Checks if there is such match of Name and Password in Data Base.
	 * @param customerName name to match.
	 * @param customerPassword password to match.
	 * @return true if match was found otherwise false.
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean customerLogin(String customerName, String customerPassword) throws ApplicationException {
		try {
			Query loginQuery = entityManager.createQuery("SELECT customer FROM Customer as customer WHERE customerName =:customerNameObj AND customerPassword =:customerPasswordObj");
			loginQuery.setParameter("customerNameObj", customerName);
			loginQuery.setParameter("customerPasswordObj", customerPassword);
			return true;
		} catch (NoResultException nre) {
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CustomerDao, customerLogin(String customerName, String customerPassword); FAILED");
		}
	}
}