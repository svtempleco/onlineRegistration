package com.dronamraju.svtemple.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "SVTC_PARAKAMANI_PAYMENT_INFORMATION", schema = "parakamani")
public class UserEvent implements java.io.Serializable {
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
	private User user;
	private Event event;

	public UserEvent() {
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

	@Transient
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Transient
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

	@Override
	public String toString() {
		return "UserEvent{" +
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
				", user=" + user +
				", event=" + event +
				'}';
	}
}