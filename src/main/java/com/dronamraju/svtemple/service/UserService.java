package com.dronamraju.svtemple.service;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.dronamraju.svtemple.dao.UserDAO;
import com.dronamraju.svtemple.model.Donation;
import com.dronamraju.svtemple.model.User;
import com.dronamraju.svtemple.model.UserEvent;
import com.dronamraju.svtemple.model.Event;

import java.util.List;

/**
 * Created by mdronamr on 2/24/17.
 */

@ManagedBean(name = "userService")
@ApplicationScoped
public class UserService {
    UserDAO userDAO = new UserDAO();

    public void saveEvent(Event event) {
        userDAO.saveEvent(event);
    }

    public User saveUser(User user) {
        return userDAO.saveUser(user);
    }

    public User findUser(String email, String password) {
        return userDAO.findUser(email, password);
    }

    public List<User> findAllUsers() {
        return userDAO.findAllUsers();
    }

    public List<UserEvent> findUserEvents(String orderNumber) {
        return userDAO.findUserEvents(orderNumber);
    }

    public List<UserEvent> findUserEvents() {
        return userDAO.findUserEvents();
    }

    public List<Donation> findDonations(Long userId) {
        return userDAO.findDonations(userId);
    }

    public boolean orderNumberExists(String orderNumber) {
        return userDAO.orderNumberExists(orderNumber);
    }

    public String findValue(String name) {
        return userDAO.findValue(name);
    }

    public void deleteUserEvents(String orderNumber) {
        userDAO.deleteUserEvents(orderNumber);
    }

    public String findPassword(String email) {
        return userDAO.findPassword(email);
    }

    public void updateUserPassword(String email, String newPassword) {
        userDAO.updateUserPassword(email, newPassword);
    }

    public void detachUser(User loggedInUser) {
        userDAO.detachUser(loggedInUser);
    }
}
