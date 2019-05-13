package com.yakov.coupons.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 
 * @author Yakov
 * Bean class for company data object
 */
@Entity
@Table(name="company")
public class Company {
	
	@GeneratedValue
	@Id
	@Column(name="COMP_ID", nullable=false)
	private long companyId;

	@Column(name="COMP_NAME", nullable=false)
 	private String companyName;

	@Column(name="COMP_PASSWORD", nullable=false)	
	private String companyPassword;

	@Column(name="COMP_EMAIL", nullable=false)		
	private String companyEmail;

	public Company() {
		super();
	}

	public Company(String companyName, String companyPassword, String companyEmail) {
		super();
		this.companyName = companyName;
		this.companyPassword = companyPassword;
		this.companyEmail = companyEmail;
	}
	
	public Company(Long companyId, String companyName, String companyPassword, String companyEmail) {
		this( companyName,  companyPassword,  companyEmail);
		this.companyId = companyId;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyEmail == null) ? 0 : companyEmail.hashCode());
		result = prime * result + (int) (companyId ^ (companyId >>> 32));
		result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
		result = prime * result + ((companyPassword == null) ? 0 : companyPassword.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (companyEmail == null) {
			if (other.companyEmail != null)
				return false;
		} else if (!companyEmail.equals(other.companyEmail))
			return false;
		if (companyId != other.companyId)
			return false;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
			return false;
		if (companyPassword == null) {
			if (other.companyPassword != null)
				return false;
		} else if (!companyPassword.equals(other.companyPassword))
			return false;
		return true;
	}

	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyPassword() {
		return companyPassword;
	}
	public void setCompanyPassword(String companyPassword) {
		this.companyPassword = companyPassword;
	}
	public String getCompanyEmail() {
		return companyEmail;
	}
	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}
	
	@Override
	public String toString() {
		return "([Company ID: " + this.getCompanyId() + "] [Company name: " + this.getCompanyName() + "] [Company password: " + this.getCompanyPassword() 
		+ "] [Company email: " + this.getCompanyEmail() + "])";
	}
}
