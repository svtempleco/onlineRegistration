package com.dronamraju.svtemple.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.List;
import java.io.Serializable;

import com.dronamraju.svtemple.model.User;
import com.dronamraju.svtemple.model.Event;
import com.dronamraju.svtemple.model.UserEvent;
import com.dronamraju.svtemple.util.FacesUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.dronamraju.svtemple.service.EventService;

/**
 * Created by mdronamr on 2/23/17.
 */

@ManagedBean(name = "eventBean")
@SessionScoped
public class EventBean implements Serializable {

    private static Log log = LogFactory.getLog(EventBean.class);

    public Event event;

    @ManagedProperty("#{eventService}")
    private EventService eventService;

    private FacesContext facesContext = FacesContext.getCurrentInstance();

    private List<Event> events;

    private List<Event> filteredEvents;

    private List<UserEvent> filteredUserEvents;

    private Event selectedEvent;

    private List<Event> selecetdEvents;

    private List<UserEvent> userEvents;

    public List<Event> getFilteredEvents() {
        return filteredEvents;
    }

    public void setFilteredEvents(List<Event> filteredEvents) {
        this.filteredEvents = filteredEvents;
    }

    public Event getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @PostConstruct
    public void init() {
        events = eventService.getEvents();
        event = new Event(); //This is required for: Target Unreachable, 'null' returned null
    }

    public void addEvent() {
        log.info("addEvent()...");
        User loggedInUser = FacesUtil.getUserFromSession();
        Boolean hasValidationErrors = false;


        if (event.getEventDescription() == null || event.getEventDescription().trim().length() < 1) {
            FacesUtil.getFacesContext().addMessage("description", new FacesMessage(FacesMessage.SEVERITY_ERROR, "A Valid Description is required.", null));
            hasValidationErrors = true;
        }

        if (hasValidationErrors) {
            log.info("Validation Failed...");
            return;
        }

        //Event event = new Event(name, description, price, location, schedule, Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), "Manu", "Manu");
        event.setCreatedDate(Calendar.getInstance().getTime());
        event.setUpdatedDate(Calendar.getInstance().getTime());
        event.setCreatedUser(loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
        event.setUpdatedUser(loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
        //log.info("event: " + event);
        eventService.saveEvent(event);
        log.info("New Temple Service has been successfully saved.");
        events = eventService.getEvents();
        FacesUtil.redirect("events.xhtml");
    }

    public void updateEvent() {
        //log.info("selectedEvent: " + selectedEvent);
        if (FacesUtil.getUserFromSession() == null) {
            FacesUtil.redirect("login.xhtml");
        }
        User loggedInUser = FacesUtil.getUserFromSession();
        selectedEvent.setCreatedDate(Calendar.getInstance().getTime());
        selectedEvent.setUpdatedDate(Calendar.getInstance().getTime());
        selectedEvent.setCreatedUser(loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
        selectedEvent.setUpdatedUser(loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
        eventService.updateEvent(selectedEvent);
        log.info("Temple Service has been successfully updated.");
        events = eventService.getEvents();
        FacesUtil.redirect("events.xhtml");
    }

    public String deleteEvent() {
        if (FacesUtil.getUserFromSession() == null) {
            FacesUtil.redirect("login.xhtml");
        }
        eventService.removeEvent(selectedEvent);
        events = eventService.getEvents();
        selectedEvent = null;
        FacesUtil.redirect("events.xhtml");
        return null;
    }

    public String cancel() {
        log.info("cancel()..");
        events = eventService.getEvents();
        FacesUtil.redirect("events.xhtml");
        return null;
    }

    public void addNewService() {
        try {
            FacesUtil.redirect("event.xhtml");
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    public EventService getEventService() {
        return eventService;
    }

    public List<Event> getSelecetdEvents() {
        return selecetdEvents;
    }

    public void setSelecetdEvents(List<Event> selecetdEvents) {
        this.selecetdEvents = selecetdEvents;
    }

    public List<UserEvent> getUserEvents() {
        return userEvents;
    }

    public void setUserEvents(List<UserEvent> userEvents) {
        this.userEvents = userEvents;
    }

    public List<UserEvent> getFilteredUserEvents() {
        return filteredUserEvents;
    }

    public void setFilteredUserEvents(List<UserEvent> filteredUserEvents) {
        this.filteredUserEvents = filteredUserEvents;
    }
}
