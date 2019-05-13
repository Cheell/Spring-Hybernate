package com.yakov.coupons.dao;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yakov.coupons.exceptions.ApplicationException;
import com.yakov.coupons.entity.Company;
import com.yakov.coupons.enums.ErrorType;
import com.yakov.coupons.utils.DateUtils;

/**
 * 
 * @author Yakov
 *	Dao that works with Companies.
 */
@Repository
public class CompanyDao{
	@PersistenceContext(unitName="demo")
	private EntityManager entityManager;
	/**
	 * Adds new company to the company table.
	 * @param company to add to the table.
	 * @throws ApplicationException 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void createCompany(Company company) throws ApplicationException {
		try {
			entityManager.persist(company);
		} catch (Exception e) {
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CompanyDao, creatCompany(); FAILED");
		}
	}

	
	/**
	 * Finds a company by company ID.
	 * @param companyId Id to look for the company.
	 * @return company.
	 * @throws ApplicationException 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public Company getCompanyByCompanyId(long companyId) throws ApplicationException {
		try {
			return entityManager.find(Company.class, companyId);
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CompanyDao, getCompanyByComapnyId(); FAILED");
		}
	}

	/**
	 * Finds a company by company Name.
	 * @param companyName to look for the company.
	 * @return company.
	 * @throws ApplicationException 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public Company getCompanyByCompanyName(String companyName) throws ApplicationException {
		try {
			Query query = entityManager.createQuery("SELECT company FROM Company as company WHERE companyName =:companyNameObj");
			query.setParameter("companyNameObj", companyName);
			return (Company)query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() 
					+ "Error in CompanyDao, getCompanyByCompanyName(String companyName);  Failed");
		}
	}
	
	/**
	 * Finds a company by company Email.
	 * @param email to look for the company.
	 * @return company.
	 * @throws ApplicationException 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public Company getCompanyByCompanyEmail(String companyEmail) throws ApplicationException {
		try {
			Query query = entityManager.createQuery("SELECT company FROM Company as company WHERE companyEmail =:companyEmailObj");
			query.setParameter("companyEmailObj", companyEmail);
			return (Company)query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() 
					+ "Error in CompanyDao, getCompanyByCompanyEmail(String companyEmail);  Failed");
		}
	}
	
	/**
	 * Extracts data of all companies from Data Base.
	 * @return List of all companies.
	 * @throws ApplicationException 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public List<Company> getAllCompanies() throws ApplicationException{
		
		List<Company> companiesList = new ArrayList<>();
		try {
			Query getQuery = entityManager.createQuery("SELECT company FROM Company As company");
			companiesList = getQuery.getResultList();
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CompanyDao, getAllCompanies(); Failed");
		}
		return companiesList;
	}
	
	/**
	 * Removes company with certain ID from Data Base. 
	 * @param companyId company id to remove.
	 * @throws ApplicationException 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void removeCompanyById(long companyId) throws ApplicationException {
		try {
			Company company = getCompanyByCompanyId(companyId);
			entityManager.remove(company);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() 
					+ "Error in CompanyDao, removeCompanyById(long companyId); Failed");
		}
	}
	
	/**
	 * Updates data of company using company Id as a search parameter, that we receive from company.
	 * @param company that we update.
	 * @throws ApplicationException 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateCompany(Company company) throws ApplicationException {
		try {
			entityManager.merge(company);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() 
					+ "Error in CompanyDao, updateCompany(Company company); Failed");
		}
	}
	
	/**
	 * Checks if there is such match of company Name and Password in Data Base.
	 * @param companyName name to match.
	 * @param companyPassword password to match.
	 * @return true if match was found otherwise false.
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean companyLogin(String companyName, String companyPassword) throws ApplicationException {
		try {
			Query loginQuery = entityManager.createQuery("SELECT company FROM Company as company WHERE companyName =:companyNameObj AND companyPassword =:companyPasswordObj");
			loginQuery.setParameter("companyNameObj", companyName);
			loginQuery.setParameter("companyPasswordObj", companyPassword);
			return true;
		} catch (NoResultException nre) {
			return false;
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() 
					+ "Error in CompanyDao, companyLogin(String companyName, String companyPassword); Failed");
		}
	}
	
}