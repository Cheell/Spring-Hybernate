package com.yakov.coupons.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yakov.coupons.enums.CouponTypes;
/**
 * 
 * @author Yakov
 * bean class for coupon data object
 */

@Entity
@Table(name="coupon")
public class Coupon {
	
	@GeneratedValue
	@Id
	@Column(name="COUP_ID", nullable=false)
	private long couponId;
	
	@Column(name="COUP_TITLE", nullable=false)
	private String couponTitle;
	
	@Column(name="COUP_START_DATE", nullable=false)
	private String couponStartDate;
	
	@Column(name="COUP_END_DATE", nullable=false)
	private String couponEndDate;

	@Column(name="COUP_AMMOUNT", nullable=false)
	private int couponAmmount;
		
	@Column(name="COUP_TYPE", nullable=false)	
	private CouponTypes couponType;

	@Column(name="COUP_MESSAGE", nullable=false)	
	private String couponMessage;

	@Column(name="COUP_PRICE", nullable=false)	
	private double couponPrice;
	
	@Column(name="COUP_IMAGE", nullable=false)
	private String couponImage;
	
	@Column(name="COMP_ID", nullable=false)
	private long companyId;

	public Coupon () {
		super();
	}
	
	
	public Coupon(String couponTitle, String couponStartDate, String couponEndDate, int couponAmmount,
			CouponTypes couponType, String couponMessage, double couponPrice, String couponImage, long companyId) {
		super();
		this.couponTitle = couponTitle;
		this.couponStartDate = couponStartDate;
		this.couponEndDate = couponEndDate;
		this.couponAmmount = couponAmmount;
		this.couponType = couponType;
		this.couponMessage = couponMessage;
		this.couponPrice = couponPrice;
		this.couponImage = couponImage;
		this.companyId = companyId;
	}
	
	public Coupon(long id, String couponTitle, String couponStartDate, String couponEndDate, int couponAmmount,
			CouponTypes couponType, String couponMessage, double couponPrice, String couponImage, long companyId) {
		this(couponTitle, couponStartDate, couponEndDate, couponAmmount, couponType, couponMessage, couponPrice, couponImage, companyId);
		this.couponId = id;
	}

	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + couponAmmount;
		result = prime * result + ((couponEndDate == null) ? 0 : couponEndDate.hashCode());
		result = prime * result + (int) (couponId ^ (couponId >>> 32));
		result = prime * result + ((couponImage == null) ? 0 : couponImage.hashCode());
		result = prime * result + ((couponMessage == null) ? 0 : couponMessage.hashCode());
		long temp;
		temp = Double.doubleToLongBits(couponPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((couponStartDate == null) ? 0 : couponStartDate.hashCode());
		result = prime * result + ((couponTitle == null) ? 0 : couponTitle.hashCode());
		result = prime * result + ((couponType == null) ? 0 : couponType.hashCode());
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
		Coupon other = (Coupon) obj;
		if (couponAmmount != other.couponAmmount)
			return false;
		if (couponEndDate == null) {
			if (other.couponEndDate != null)
				return false;
		} else if (!couponEndDate.equals(other.couponEndDate))
			return false;
		if (couponId != other.couponId)
			return false;
		if (couponImage == null) {
			if (other.couponImage != null)
				return false;
		} else if (!couponImage.equals(other.couponImage))
			return false;
		if (couponMessage == null) {
			if (other.couponMessage != null)
				return false;
		} else if (!couponMessage.equals(other.couponMessage))
			return false;
		if (Double.doubleToLongBits(couponPrice) != Double.doubleToLongBits(other.couponPrice))
			return false;
		if (couponStartDate == null) {
			if (other.couponStartDate != null)
				return false;
		} else if (!couponStartDate.equals(other.couponStartDate))
			return false;
		if (couponTitle == null) {
			if (other.couponTitle != null)
				return false;
		} else if (!couponTitle.equals(other.couponTitle))
			return false;
		if (couponType != other.couponType)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Coupon [couponId=" + couponId + ", couponTitle=" + couponTitle + ", couponStartDate=" + couponStartDate
				+ ", couponEndDate=" + couponEndDate + ", couponAmmount=" + couponAmmount + ", couponType=" + couponType
				+ ", couponMessage=" + couponMessage + ", couponPrice=" + couponPrice + ", couponImage=" + couponImage
				+ ", companyId=" + companyId + "]";
	}

	public long getCouponId() {
		return couponId;
	}
	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}
	public String getCouponTitle() {
		return couponTitle;
	}
	public void setCouponTitle(String couponTitle) {
		this.couponTitle = couponTitle;
	}
	public String getCouponStartDate() {
		return couponStartDate;
	}
	
	public void setCouponStartDate(String couponStartDate) {
		this.couponStartDate = couponStartDate;
	}
	public String getCouponEndDate() {
		return couponEndDate;
	}
	public void setCouponEndDate(String couponEndDate) {
		this.couponEndDate = couponEndDate;
	}
	public int getCouponAmmount() {
		return couponAmmount;
	}
	public void setCouponAmmount(int couponAmmount) {
		this.couponAmmount = couponAmmount;
	}
	public String getCouponMessage() {
		return couponMessage;
	}
	public void setCouponMessage(String couponMessage) {
		this.couponMessage = couponMessage;
	}
	public double getCouponPrice() {
		return couponPrice;
	}
	public void setCouponPrice(double couponPrice) {
		this.couponPrice = couponPrice;
	}
	public String getCouponImage() {
		return couponImage;
	}
	public void setCouponImage(String couponImage) {
		this.couponImage = couponImage;
	}
	public CouponTypes getCouponType() {
		return this.couponType;
	}
	public void setCouponType(CouponTypes couponType) {
		this.couponType = couponType;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	
	
	
}