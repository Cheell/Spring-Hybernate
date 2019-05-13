package com.yakov.coupons.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Yakov
 * Bean class used for customer data object.
 */
@Entity
@Table(name="customer")

public class Customer {
	@GeneratedValue
	@Id
	@Column(name="CUST_ID", nullable=false)
	private long customerId;
	
	@Column(name="CUST_NAME", nullable=false)
	private String customerName;
	
	@Column(name="CUST_PASSWORD", nullable=false)
	private String customerPassword;

	public Customer() {
		super();
	}
	public Customer(String name, String pass) {
		super();
		this.customerName = name;
		this.customerPassword = pass;
	}
	public Customer(String name, String pass, long id) {
		this(name, pass);
		this.customerId = id;
	}
	
	
	
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerPassword() {
		return customerPassword;
	}
	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}
	
	public String toString() {
		return "([Customer ID: " + this.getCustomerId() + "] [Customer name: " + this.getCustomerName() + "] [Customer password: " + this.getCustomerPassword() 
		+ "])";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (customerId ^ (customerId >>> 32));
		result = prime * result + ((customerName == null) ? 0 : customerName.hashCode());
		result = prime * result + ((customerPassword == null) ? 0 : customerPassword.hashCode());
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
		Customer other = (Customer) obj;
		if (customerId != other.customerId)
			return false;
		if (customerName == null) {
			if (other.customerName != null)
				return false;
		} else if (!customerName.equals(other.customerName))
			return false;
		if (customerPassword == null) {
			if (other.customerPassword != null)
				return false;
		} else if (!customerPassword.equals(other.customerPassword))
			return false;
		return true;
	}

}
