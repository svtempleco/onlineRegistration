package com.dronamraju.svtemple.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SVTC_PARAKAMANI_DONATION_INFORMATION", schema = "parakamani")
public class Donation implements java.io.Serializable {
	private Long userId;
	private Long eventId;
	private Long eventServiceId;
	private Date eventDate;
	private Double amount;
	private String paymentMethod;
	private String checkNum;
	private String ccNumber;
	private Date updatedDate;
	private Date createdDate;
	private String updatedUser;
	private String createdUser;
	private String printedFlag;
	private Date annualServiceBeginDate;
	private Date annualServiceEndDate;
	private Date donationDate;
	private String checkComments;
	private String countForPledge;
	private User user;
	private Event event;

	public Donation() {
	}

	@Id
	@Column(name = "DONOR_ID")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "EVENT_ID")
	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "DONOR_ID")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "EVENT_ID", insertable = false, updatable = false)
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	@Column(name = "CREATE_DATE")
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "UPDATE_DATE")
	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Column(name = "EVENT_SERVICE_ID")
	public Long getEventServiceId() {
		return eventServiceId;
	}

	public void setEventServiceId(Long eventServiceId) {
		this.eventServiceId = eventServiceId;
	}

	@Column(name = "EVENT_DATE")
	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	@Column(name = "AMOUNT")
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "PAYMENT_METHOD")
	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Column(name = "CHECK_NUM")
	public String getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(String checkNum) {
		this.checkNum = checkNum;
	}

	@Column(name = "CC_NUMBER")
	public String getCcNumber() {
		return ccNumber;
	}

	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}

	@Column(name = "UPDATE_USER")
	public String getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}

	@Column(name = "CREATE_USER")
	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	@Column(name = "PRINTED_FLAG")
	public String getPrintedFlag() {
		return printedFlag;
	}

	public void setPrintedFlag(String printedFlag) {
		this.printedFlag = printedFlag;
	}

	@Column(name = "ANN_SERVICE_BEGIN_DATE")
	public Date getAnnualServiceBeginDate() {
		return annualServiceBeginDate;
	}

	public void setAnnualServiceBeginDate(Date annualServiceBeginDate) {
		this.annualServiceBeginDate = annualServiceBeginDate;
	}

	@Column(name = "ANN_SERVICE_END_DATE")
	public Date getAnnualServiceEndDate() {
		return annualServiceEndDate;
	}

	public void setAnnualServiceEndDate(Date annualServiceEndDate) {
		this.annualServiceEndDate = annualServiceEndDate;
	}

	@Column(name = "DONATION_DATE")
	public Date getDonationDate() {
		return donationDate;
	}

	public void setDonationDate(Date donationDate) {
		this.donationDate = donationDate;
	}

	@Column(name = "CHECK_COMMENTS")
	public String getCheckComments() {
		return checkComments;
	}

	public void setCheckComments(String checkComments) {
		this.checkComments = checkComments;
	}

	@Column(name = "COUNT_FOR_PLEDGE")
	public String getCountForPledge() {
		return countForPledge;
	}

	public void setCountForPledge(String countForPledge) {
		this.countForPledge = countForPledge;
	}

	@Override
	public String toString() {
		return "Donation{" +
				"userId=" + userId +
				", eventId=" + eventId +
				", eventServiceId=" + eventServiceId +
				", eventDate=" + eventDate +
				", amount=" + amount +
				", paymentMethod='" + paymentMethod + '\'' +
				", checkNum='" + checkNum + '\'' +
				", ccNumber='" + ccNumber + '\'' +
				", updatedDate=" + updatedDate +
				", createdDate=" + createdDate +
				", updatedUser='" + updatedUser + '\'' +
				", createdUser='" + createdUser + '\'' +
				", printedFlag='" + printedFlag + '\'' +
				", annualServiceBeginDate=" + annualServiceBeginDate +
				", annualServiceEndDate=" + annualServiceEndDate +
				", donationDate=" + donationDate +
				", checkComments='" + checkComments + '\'' +
				", countForPledge='" + countForPledge + '\'' +
				", user=" + user +
				", event=" + event +
				'}';
	}
}