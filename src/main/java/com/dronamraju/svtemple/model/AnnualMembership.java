package com.dronamraju.svtemple.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SVTC_PARAKAMANI_ANNUAL_MEMBERSHIP", schema = "parakamani")
public class AnnualMembership implements java.io.Serializable {
	private Long userId;
	private Date startDate;
	private Date endDate;
	private Double amount;
	private Double amountLeft;
	private Date createdDate;
	private String createdUser;
	private User user;

	public AnnualMembership() {
	}

	@Id
	@Column(name = "DONOR_ID")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "START_DATE")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "END_DATE")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "AMOUNT")
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "AMOUNT_LEFT")
	public Double getAmountLeft() {
		return amountLeft;
	}

	public void setAmountLeft(Double amountLeft) {
		this.amountLeft = amountLeft;
	}

	@Column(name = "CREATE_DATE")
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "CREATE_USER")
	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "DONOR_ID")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}