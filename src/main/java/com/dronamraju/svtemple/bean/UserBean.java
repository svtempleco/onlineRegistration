package com.dronamraju.svtemple.bean;

import com.dronamraju.svtemple.model.*;
import com.dronamraju.svtemple.service.EventService;
import com.dronamraju.svtemple.service.UserService;
import com.dronamraju.svtemple.service.ParakamaniService;
import com.dronamraju.svtemple.util.AES;
import com.dronamraju.svtemple.util.FacesUtil;
import com.dronamraju.svtemple.util.SendEmail;
import com.dronamraju.svtemple.util.Util;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;


@ManagedBean(name = "userBean")
@SessionScoped
public class UserBean implements Serializable {

	private static Log log = LogFactory.getLog(UserBean.class);

	@ManagedProperty("#{userService}")
	private UserService userService;

	@ManagedProperty("#{parakamaniService}")
	private ParakamaniService parakamaniService;

	@ManagedProperty("#{eventService}")
	private EventService eventService;

	private User user = new User();

	private Date dateAndTime;

	private String additionalNotes;

	private Double totalAmount = 0.00;

	private List<UserEvent> userEvents;

	private List<Event> events = new ArrayList<>();

	private List<Event> selectedEvents = new ArrayList<>();

	private List<Event> filteredEvents;

	private User loggedInUser;

	private List<User> users;

	private List<Donation> donations;

	@PostConstruct
	public void init() {
		user = new User();
		loggedInUser = FacesUtil.getUserFromSession();
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void login() {
		log.info("login()...");
		try {
			Boolean hasValidationErrors = false;
			if (user.getEmail() == null || user.getEmail().trim().length() < 1 || !Util.isValidEmail(user.getEmail())) {
				FacesUtil.getFacesContext().addMessage("email", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A Valid email is required.", null));
				hasValidationErrors = true;
			}
			if (user.getPassword() == null || user.getPassword().trim().length() < 1) {
				FacesUtil.getFacesContext().addMessage("password", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A Valid Password is required.", null));
				hasValidationErrors = true;
			}
			if (hasValidationErrors) {
				log.info("Validation Failed...");
				return;
			}
			loggedInUser = userService.findUser(user.getEmail(), AES.encrypt(user.getPassword()));
			FacesUtil.setUserInSession(loggedInUser);
			if (loggedInUser == null) {
				FacesUtil.getFacesContext().addMessage("password", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Either invalid login or user does not exist.", null));
				return;
			}
			log.info(loggedInUser.getFirstName() + " " + loggedInUser.getLastName() + " has logged in...");
			user = loggedInUser;
			selectedEvents = new ArrayList<>();
            events = parakamaniService.getEvents();
			FacesUtil.redirect("purchaseServices.xhtml");
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

    public void goToUserProfilePage() {
        log.info("goToUserProfilePage()..");
        FacesUtil.redirect("userRegistration.xhtml");
    }

    public void goToMyDonationsPage() {
        log.info("goToMyDonationsPage()..");
        if (loggedInUser != null) {
            donations = userService.findDonations(loggedInUser.getUserId());
            FacesUtil.redirect("donations.xhtml");
        } else {
            FacesUtil.redirect("login.xhtml");
        }
    }

    public void goToPurchaseServicesPage() {
        log.info("goToPurchaseServicesPage()..");
        log.info("loggedInUser: " + loggedInUser);
        if (loggedInUser != null) {
            events = parakamaniService.getEvents();
            FacesUtil.redirect("purchaseServices.xhtml");
        } else {
            FacesUtil.redirect("login.xhtml");
        }
    }

    public void registerOrUpdateUser() {
		log.info("registerOrUpdateUser()...");
		try {
			Boolean hasValidationErrors = false;
			if (FacesUtil.getUserFromSession() != null) {
				loggedInUser = FacesUtil.getUserFromSession();
			}

			if (user.getFirstName() == null || user.getFirstName().trim().length() < 1) {
				FacesUtil.getFacesContext().addMessage("firstName", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A valid fisrt name is required.", null));
				hasValidationErrors = true;
			}

			if (user.getLastName() == null || user.getLastName().trim().length() < 1) {
				FacesUtil.getFacesContext().addMessage("lastName", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A valid last name is required.", null));
				hasValidationErrors = true;
			}

			if (user.getEmail() == null || user.getEmail().trim().length() < 1 || !Util.isValidEmail(user.getEmail())) {
				FacesUtil.getFacesContext().addMessage("email", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A valid email is required.", null));
				hasValidationErrors = true;
			}

			if (user.getPhone() == null || user.getPhone().trim().length() < 1 || !Util.isValidPhoneNumber(user.getPhone())) {
				FacesUtil.getFacesContext().addMessage("phoneNumber", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A valid phone number is required.", null));
				hasValidationErrors = true;
			}

			if (loggedInUser == null) {
				if (StringUtils.isEmpty(user.getPassword()) || !(user.getPassword().equals(user.getRePassword()))) {
					FacesUtil.getFacesContext().addMessage("password", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password should be at least 10 characters long and should match with re-entered.", null));
					hasValidationErrors = true;
				}
			} else {
				if (user.getRePassword() != null) {
					if (StringUtils.isEmpty(user.getPassword()) || !(user.getPassword().equals(user.getRePassword()))) {
						FacesUtil.getFacesContext().addMessage("password", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password should be at least 10 characters long and should match with re-entered.", null));
						hasValidationErrors = true;
					}
				}
			}

			if (user.getAddress1() == null || user.getAddress1().trim().length() < 1) {
				FacesUtil.getFacesContext().addMessage("streetAddress", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A valid street address is required.", null));
				hasValidationErrors = true;
			}

			if (user.getAddress2() == null || user.getAddress2().trim().length() < 1) {
				FacesUtil.getFacesContext().addMessage("streetAddress", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A valid street address is required.", null));
				hasValidationErrors = true;
			}

			if (user.getCity() == null || user.getCity().trim().length() < 1) {
				FacesUtil.getFacesContext().addMessage("phoneNumber", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A valid phone number is required.", null));
				hasValidationErrors = true;
			}

			if (user.getState() == null || user.getState().trim().length() < 1) {
				FacesUtil.getFacesContext().addMessage("state", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A valid state is required.", null));
				hasValidationErrors = true;
			}

			if (user.getZip() == null || user.getZip().trim().length() < 1 || !Util.isValidZip(user.getZip())) {
				FacesUtil.getFacesContext().addMessage("zip", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A valid zip is required.", null));
				hasValidationErrors = true;
			}

			if (hasValidationErrors) {
				log.info("Validation Failed...");
				selectedEvents = new ArrayList<>();
				return;
			}
			user.setCreateDate(Calendar.getInstance().getTime());
			user.setUpdateDate(Calendar.getInstance().getTime());
			user.setCreateUser(user.getFirstName() + " " + user.getLastName());
			user.setUpdateUser(user.getFirstName() + " " + user.getLastName());
			user.setAdmin(false);
			user.setPassword(AES.encrypt(user.getPassword()));
			loggedInUser = userService.saveUser(user);
			FacesUtil.setUserInSession(loggedInUser);
			StringBuilder sb = new StringBuilder();
			sb.append("<b>Hello " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName() + ",<br><br>Thank you for registering or updating your profile.</b><br></br>");
			sb.append("<b>Sri Venkateswara Swamy Temple Of Colorado</b><br>");
			sb.append("<b>1495 S Ridge Road Castle Rock CO 80104</b><br>");
			sb.append("<b>Manager: 303-898-5514 | Temple: 303-660-9555 | Email: <a href='mailto:manager@svtempleco.org'>manager@svtempleco.org</a></b><br>");
			sb.append("<b>Website: <a href='http://www.svtempleco.org/homepage.php'>http://www.svtempleco.org</a></b><br>");
			sb.append("<b>Facebook: <a href='https://www.facebook.com/SVTC.COLORADO/'>SVTC.Colorado</a></b><br>");
			sb.append("<b>PayPal User: <a href='https://www.paypal.me/svtempleco'>SVTC PayPal Link</a></b><br>");
			String recipients = "manudr@hotmail.com";
			SendEmail.sendMail(sb.toString(), loggedInUser.getEmail(), recipients);
			FacesUtil.redirect("login.xhtml");
		} catch(Exception exception) {
			Optional<Throwable> rootCause = Stream.iterate(exception, Throwable::getCause).filter(element -> element.getCause() == null).findFirst();
			FacesUtil.getFacesContext().addMessage("selectedEvents", new FacesMessage(FacesMessage.SEVERITY_ERROR, rootCause.toString(), null));
			log.error("error occurred: ", exception);
			return;
		}
	}

	public void purchaseServices() {
		log.info("purchaseServices()...");
		try {
			Boolean hasValidationErrors = false;
			//log.info("User: " + user);
			totalAmount = 0.00;

			if (FacesUtil.getUserFromSession() != null) {
				loggedInUser = FacesUtil.getUserFromSession();
			}

			//log.info("loggedInUser in session: " + loggedInUser);
//			log.info("selectedEvents: " + selectedEvents);

			if (selectedEvents == null || selectedEvents.size() < 1) {
				FacesUtil.getFacesContext().addMessage("selectedEvents", new FacesMessage(FacesMessage.SEVERITY_ERROR, "One or more services must be selecetd.", null));
				hasValidationErrors = true;
			}

			//log.info("additionalNotes: " + additionalNotes);

			//log.info("dateAndTime: " + dateAndTime);

			if (dateAndTime != null && !(Util.isValidDate(dateAndTime))) {
				log.info("date failed");
				FacesUtil.getFacesContext().addMessage("dateAndTime", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A aalid date and time is required.", null));
				hasValidationErrors = true;
			}

			if (hasValidationErrors) {
				//log.info("Validation Failed...");
				selectedEvents = new ArrayList<>();
				return;
			}

			String orderNumber = Util.randomAlphaNumeric(10);

			while (userService.orderNumberExists(orderNumber)) {
				orderNumber = Util.randomAlphaNumeric(10);
			}

			//log.info("orderNumber: " + orderNumber + " created at: " + Calendar.getInstance().getTime());

			FacesUtil.getRequest().getSession().setAttribute("orderNumber", orderNumber);

			for (Event selectedProd : selectedEvents) {
				UserEvent userEvent = new UserEvent();
				userEvent.setUserId(loggedInUser.getUserId());
				userEvent.setEventId(selectedProd.getEventId());
				userEvent.setUser(loggedInUser);
				userEvent.setEvent(selectedProd);
				userEvent.setCreatedDate(Calendar.getInstance().getTime());
				userEvent.setUpdatedDate(Calendar.getInstance().getTime());
				userEvent.setCreatedUser(loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
				userEvent.setUpdatedUser(loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
				//log.info("userEvent: " + userEvent);
				eventService.saveUserEvent(userEvent);
			}

			userEvents = userService.findUserEvents(orderNumber);

			StringBuilder sb = new StringBuilder();
			sb.append("<h4>Thank you. You have purchased the below temple services:</h4>");
			for (UserEvent userEvent : userEvents) {
//				log.info("userEvent: " + userEvent);
				//sb.append("<b>Order Number: </b>" + userEvent.getOrderNumber() + "<br></br>");
				sb.append("<b>Event Description: </b>" + userEvent.getEvent().getEventDescription() + "<br></br>");
				for (Service service : userEvent.getEvent().getServices()) {
					totalAmount = totalAmount + service.getServiceCost();
					sb.append("<b>Service Description: </b>" + service.getServiceDescription() + "<br></br>");
					sb.append("<b>Price: $</b>" + service.getServiceCost() + "<br></br>");
					sb.append("<b>Location: </b>" + userEvent.getEvent().getVenue() + "<br></br>");
					sb.append("<b>Date of Service: </b>" + userEvent.getEvent().getEventDate() + "<br></br>");
				}
				log.info("totalAmount: " + totalAmount);
				/*
				if (userEvent.getDateAndTime() != null) {
					sb.append("<b>Date and Time: </b>" + DateFormat.getDateTimeInstance(
							DateFormat.MEDIUM, DateFormat.SHORT).format(userEvent.getDateAndTime()) + "<br></br>");
				}

				if (userEvent.getNotes() != null && userEvent.getNotes().length() > 0) {
					sb.append("<b>Notes: </b>" + userEvent.getNotes() + "<br></br>");
				}
				*/
				sb.append("<br></br>");
			}
			sb.append("<b>Total Amount to be paid: </b>$" + totalAmount + "<br></br><br></br>");
			sb.append("<b>Thank you</b><br></br>");
			sb.append("<b>Sri Venkateswara Swamy Temple Of Colorado</b><br></br>");
			sb.append("<b>1495 S Ridge Road Castle Rock CO 80104</b><br></br>");
			sb.append("<b>Manager: 303-898-5514 | Temple: 303-660-9555 | Email: <a href='mailto:manager@svtempleco.org'>manager@svtempleco.org</a></b><br></br>");
			sb.append("<b>Website: <a href='http://www.svtempleco.org/homepage.php'>http://www.svtempleco.org</a></b><br></br>");
			sb.append("<b>Facebook: <a href='https://www.facebook.com/SVTC.COLORADO/'>SVTC.Colorado</a></b><br></br>");
			sb.append("<b>PayPal User: <a href='https://www.paypal.me/svtempleco'>SVTC PayPal Link</a></b><br></br>");
			//String recipients = userService.findValue("recipients");
			String recipients = "manudr@hotmail.com";
			SendEmail.sendMail(sb.toString(), user.getEmail(), recipients);
			loggedInUser = user;
			FacesUtil.setUserInSession(loggedInUser);
//			log.info("loggedInUser: " + loggedInUser);
			FacesUtil.redirect("payment.xhtml");
		} catch(Exception exception) {
			Optional<Throwable> rootCause = Stream.iterate(exception, Throwable::getCause).filter(element -> element.getCause() == null).findFirst();
			FacesUtil.getFacesContext().addMessage("selectedEvents", new FacesMessage(FacesMessage.SEVERITY_ERROR, rootCause.toString(), null));
			log.error("error occurred: ", exception);
			return;
		}
	}

	public void cancel() {
		log.info("cancel()..");
		FacesUtil.redirect("purchaseServices.xhtml");
	}

	public void goToAllServicesPage() {
		log.info("goToAllServicesPage()..");
		if (loggedInUser != null && loggedInUser.getAdmin()) {
			events = eventService.getEvents();
			FacesUtil.redirect("events.xhtml");
		} else {
			FacesUtil.redirect("login.xhtml");
		}
	}

	public void sendPassword() {
		log.info("sendPassword()..");
		try {
			if (user.getEmail() == null || user.getEmail().trim().length() < 1 || !Util.isValidEmail(user.getEmail())) {
				FacesUtil.getFacesContext().addMessage("email", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A Valid email is required.", null));
				return;
			} else {
				String password = userService.findPassword(user.getEmail());
				if (password == null) {
					FacesUtil.getFacesContext().addMessage("email", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email you provided is not found.", null));
					return;
				} else {
					String newPassword = Util.randomAlphaNumeric(6);
					userService.updateUserPassword(user.getEmail(), AES.encrypt(newPassword));
					String emailBody = "Your password is: " + newPassword + ". We recommend you to change your password.";
					SendEmail.sendMail(emailBody, user.getEmail(), null);
//					log.info("Email has been sent with password.");
					FacesUtil.getFacesContext().addMessage("email", new FacesMessage(FacesMessage.SEVERITY_INFO, "Password has been sent to your email.", null));
					return;
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public String updateUser() {
		log.info("updateUser()...");
		return null;
	}

	public void logout() {
		try {
			userService.detachUser(loggedInUser);
			FacesUtil.setUserInSession(null);
			FacesUtil.getRequest().getSession().removeAttribute("loggedInUser");
			FacesUtil.getRequest().getSession().invalidate();
			FacesUtil.redirect("login.xhtml");
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	public EventService getEventService() {
		return eventService;
	}

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

	public Date getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public String getAdditionalNotes() {
		return additionalNotes;
	}

	public void setAdditionalNotes(String additionalNotes) {
		this.additionalNotes = additionalNotes;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<UserEvent> getUserEvents() {
		return userEvents;
	}

	public void setUserEvents(List<UserEvent> userEvents) {
		this.userEvents = userEvents;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<Event> getSelectedEvents() {
		return selectedEvents;
	}

	public void setSelectedEvents(List<Event> selectedEvents) {
		this.selectedEvents = selectedEvents;
	}

	public List<Event> getFilteredEvents() {
		return filteredEvents;
	}

	public void setFilteredEvents(List<Event> filteredEvents) {
		this.filteredEvents = filteredEvents;
	}

	public User getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(User loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Donation> getDonations() {
		return donations;
	}

	public void setDonations(List<Donation> donations) {
		this.donations = donations;
	}

	public ParakamaniService getParakamaniService() {
		return parakamaniService;
	}

	public void setParakamaniService(ParakamaniService parakamaniService) {
		this.parakamaniService = parakamaniService;
	}
}