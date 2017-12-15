package com.dronamraju.svtemple.service;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.dronamraju.svtemple.dao.EventDAO;
import com.dronamraju.svtemple.model.Event;
import com.dronamraju.svtemple.model.UserEvent;

import java.util.*;

/**
 * Created by mdronamr on 2/24/17.
 */

@ManagedBean(name = "eventService")
@ApplicationScoped
public class EventService {
    EventDAO eventDAO = new EventDAO();

    public Event findEvent(Long eventId){
        return eventDAO.findEvent(eventId);
    }

    public List<Event> getEvents() {
        List<Event> list = eventDAO.getEvents();
        return list;
    }

    public List<Event> getEvents(Long userId) {
        List<Event> list = eventDAO.getEvents(userId);
        return list;
    }

    public void saveUserEvent(UserEvent userEvent){
        eventDAO.save(userEvent);
    }

    public void saveEvent(Event event) {
        eventDAO.save(event);
    }

    public void updateEvent(Event selectedEvent) {
        eventDAO.updateEvent(selectedEvent);
    }

    public void removeEvent(Event selectedEvent) {
        eventDAO.removeEvent(selectedEvent);
    }

    public Event find(Long id) {
        return eventDAO.find(id);
    }

    public List<UserEvent> findAllUserEvents() {
        return eventDAO.findAllUserEvents();
    }

}
