package com.dronamraju.svtemple.bean;

import com.dronamraju.svtemple.model.AnnualMembership;
import com.dronamraju.svtemple.model.User;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.List;
import java.io.Serializable;

import com.dronamraju.svtemple.model.Event;
import com.dronamraju.svtemple.util.FacesUtil;
import com.dronamraju.svtemple.util.Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.dronamraju.svtemple.service.ParakamaniService;
import com.dronamraju.svtemple.service.UserService;
import com.dronamraju.svtemple.model.UserEvent;
import com.dronamraju.svtemple.model.Donation;

/**
 * Created by mdronamr on 2/23/17.
 */

@ManagedBean(name = "parakamaniBean")
@SessionScoped
public class ParakamaniBean implements Serializable {

    private static Log log = LogFactory.getLog(ParakamaniBean.class);

    public User user;

    public Event event;

    @ManagedProperty("#{parakamaniService}")
    private ParakamaniService parakamaniService;

    @ManagedProperty("#{userService}")
    private UserService userService;

    private FacesContext facesContext = FacesContext.getCurrentInstance();

    private List<User> users;

    private List<Event> events;

    private List<User> filteredUsers;

    private List<Event> filteredEvents;

    private List<UserEvent> userEvents;

    private List<Donation> donations;

    private User loggedInUser;

    private List<AnnualMembership> annualMemberships;

    public List<User> getFilteredUsers() {
        return filteredUsers;
    }

    public void setFilteredUsers(List<User> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @PostConstruct
    public void init() {
        user = new User(); //This is required for: Target Unreachable, 'null' returned null
        loggedInUser = FacesUtil.getUserFromSession();
    }

    public void addUser() {
        log.info("addUser()...");
        User loggedInUser = FacesUtil.getUserFromSession();
        Boolean hasValidationErrors = false;

        if (user.getFirstName() == null || user.getFirstName().trim().length() < 1) {
            FacesUtil.getFacesContext().addMessage("name", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A Valid Service Name is required.", null));
            hasValidationErrors = true;
        }

        if (user.getEmail() == null || user.getEmail().trim().length() < 1) {
            FacesUtil.getFacesContext().addMessage("schedule", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A Valid Schedule is required.", null));
            hasValidationErrors = true;
        }

        if (user.getAddress1() == null || user.getAddress1().trim().length() < 1) {
            FacesUtil.getFacesContext().addMessage("location", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A Valid Location is required.", null));
            hasValidationErrors = true;
        }

        if (user.getCity() == null || user.getCity().trim().length() < 1) {
            FacesUtil.getFacesContext().addMessage("description", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A Valid Description is required.", null));
            hasValidationErrors = true;
        }

        if (user.getPledgeAmount() == null || user.getPledgeAmount() < 0.00 || !Util.isDouble(user.getPledgeAmount().toString())) {
            FacesUtil.getFacesContext().addMessage("price", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A Valid Pledge Amount is required.", null));
            hasValidationErrors = true;
        }

        if (hasValidationErrors) {
            log.info("Validation Failed...");
            return;
        }

        user.setCreateDate(Calendar.getInstance().getTime());
        user.setUpdateDate(Calendar.getInstance().getTime());
        user.setCreateUser(loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
        user.setUpdateUser(loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
        //log.info("user: " + user);
        parakamaniService.saveUser(user);
        log.info("New Temple Service has been successfully saved.");
        users = parakamaniService.getUsers();
        FacesUtil.redirect("users.xhtml");
    }

    public void updateUser() {
        //log.info("selectedUser: " + selectedUser);
        if (FacesUtil.getUserFromSession() == null) {
            FacesUtil.redirect("login.xhtml");
        }
        User loggedInUser = FacesUtil.getUserFromSession();
        FacesUtil.redirect("users.xhtml");
    }

    public String deleteUser() {
        if (FacesUtil.getUserFromSession() == null) {
            FacesUtil.redirect("login.xhtml");
        }
        FacesUtil.redirect("users.xhtml");
        return null;
    }

    public String cancel() {
        log.info("cancel()..");
        users = parakamaniService.getUsers();
        FacesUtil.redirect("users.xhtml");
        return null;
    }

    public void addNewService() {
        try {
            FacesUtil.redirect("user.xhtml");
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setParakamaniService(ParakamaniService parakamaniService) {
        this.parakamaniService = parakamaniService;
    }

    public ParakamaniService getParakamaniService() {
        return parakamaniService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void goToAllEventsPage() {
        log.info("goToAllEventsPage()..");
        if (events == null) {
            events = parakamaniService.getEvents();
        }
        FacesUtil.redirect("events.xhtml");
    }

    public void goToAllDevoteesAndServicesPage() {
        log.info("goToAllDevoteesAndServicesPage()..");
        if (loggedInUser != null) {
            userEvents = userService.findUserEvents();
            FacesUtil.redirect("devoteesAndServices.xhtml");
        } else {
            FacesUtil.redirect("login.xhtml");
        }
    }

    public void goToAddNewServicePage() {
        log.info("goToAddNewServicePage()..");
        if (loggedInUser != null && loggedInUser.getAdmin()) {
            FacesUtil.redirect("event.xhtml");
        } else {
            FacesUtil.redirect("login.xhtml");
        }
    }

    public void goToAllUsersPage() {
        log.info("goToAllUsersPage()..");
        if (loggedInUser != null && loggedInUser.getAdmin()) {
            users = userService.findAllUsers();
            FacesUtil.redirect("users.xhtml");
        } else {
            FacesUtil.redirect("login.xhtml");
        }
    }

    public void goToAnnualMembershipsPage() {
        log.info("goToAnnualMembershipsPage()..");
        if (loggedInUser != null && loggedInUser.getAdmin()) {
            annualMemberships = parakamaniService.findAnnualMemberships();
            FacesUtil.redirect("annualMemberships.xhtml");
        } else {
            FacesUtil.redirect("login.xhtml");
        }
    }


    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
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

    public List<AnnualMembership> getAnnualMemberships() {
        return annualMemberships;
    }

    public void setAnnualMemberships(List<AnnualMembership> annualMemberships) {
        this.annualMemberships = annualMemberships;
    }

    public List<UserEvent> getUserEvents() {
        return userEvents;
    }

    public void setUserEvents(List<UserEvent> userEvents) {
        this.userEvents = userEvents;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }
}
