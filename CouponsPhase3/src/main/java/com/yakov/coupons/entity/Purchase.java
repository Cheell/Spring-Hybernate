package com.yakov.coupons.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="purchase")

public class Purchase {

	@GeneratedValue
	@Id
	@Column(name="PURC_ID", nullable=false)
	private long purchaseId;
	
	@Column(name="CUST_ID", nullable=false)
	private long customerId;

	@Column(name="COUP_ID", nullable=false)
	private long couponId;
	
	@Column(name="COUP_AMOUNT", nullable=false)
	private int ammount;

	public long getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(long purchaseId) {
		this.purchaseId = purchaseId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	public int getAmmount() {
		return ammount;
	}

	public void setAmmount(int ammount) {
		this.ammount = ammount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ammount;
		result = prime * result + (int) (couponId ^ (couponId >>> 32));
		result = prime * result + (int) (customerId ^ (customerId >>> 32));
		result = prime * result + (int) (purchaseId ^ (purchaseId >>> 32));
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
		Purchase other = (Purchase) obj;
		if (ammount != other.ammount)
			return false;
		if (couponId != other.couponId)
			return false;
		if (customerId != other.customerId)
			return false;
		if (purchaseId != other.purchaseId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Purchase [purchaseId=" + purchaseId + ", customerId=" + customerId + ", couponId=" + couponId
				+ ", ammount=" + ammount + "]";
	}

	public Purchase() {
		super();
	}

	public Purchase(long customerId, long couponId, int ammount) {
		super();
		this.customerId = customerId;
		this.couponId = couponId;
		this.ammount = ammount;
	}

	public Purchase(long purchaseId, long customerId, long couponId, int ammount) {
		super();
		this.purchaseId = purchaseId;
		this.customerId = customerId;
		this.couponId = couponId;
		this.ammount = ammount;
	}
	
}
