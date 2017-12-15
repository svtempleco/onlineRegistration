package com.dronamraju.svtemple.service;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.dronamraju.svtemple.dao.ParakamaniDAO;
import com.dronamraju.svtemple.model.AnnualMembership;
import com.dronamraju.svtemple.model.User;
import com.dronamraju.svtemple.model.Event;


import java.util.*;

/**
 * Created by mdronamr on 05/11/17.
 */

@ManagedBean(name = "parakamaniService")
@ApplicationScoped
public class ParakamaniService {
    ParakamaniDAO parakamaniDAO = new ParakamaniDAO();

    public User findUser(Long userId){
        return parakamaniDAO.findUser(userId);
    }

    public List<User> getUsers() {
        List<User> list = parakamaniDAO.getUsers();
        return list;
    }

    public void saveUser(User user) {
        parakamaniDAO.save(user);
    }

    public void updateUser(User selectedUser) {
        parakamaniDAO.updateUser(selectedUser);
    }

    public void removeUser(User selectedUser) {
        parakamaniDAO.removeUser(selectedUser);
    }

    public User find(Long id) {
        return parakamaniDAO.find(id);
    }

    public List<Event> getEvents() {
        List<Event> events = parakamaniDAO.getEvents();
        return events;
    }

    public List<AnnualMembership> findAnnualMemberships() {
        List<AnnualMembership> annualMemberships = parakamaniDAO.findAnnualMemberships();
        return annualMemberships;
    }
}
