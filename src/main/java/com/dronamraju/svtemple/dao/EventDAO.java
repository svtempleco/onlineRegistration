package com.dronamraju.svtemple.dao;


import com.dronamraju.svtemple.model.User;
import com.dronamraju.svtemple.model.Event;
import com.dronamraju.svtemple.model.UserEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.*;

import java.util.List;

/**
 * Created by mdronamr on 2/23/17.
 */
public class EventDAO {

    private static Log log = LogFactory.getLog(EventDAO.class);
    private static EntityManagerFactory parakamaniEntityManagerFactory;
    private static EntityManager parakamaniEntityManager;

    public Event findEvent(Long eventId){
        parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
        parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
        try {
            log.info("findEvent..");
            return parakamaniEntityManager.find(Event.class, eventId);
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            parakamaniEntityManager.close();
            parakamaniEntityManagerFactory.close();
        }
    }

    public List getEvents() {
        parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
        parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
        try {
            Query query = parakamaniEntityManager.createQuery("SELECT event FROM Event event", Event.class);
            List results = query.getResultList();
            List<Event> events = query.getResultList();
//            log.info("EventDAO - Events: " + events);
            return events;
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            parakamaniEntityManager.close();
            parakamaniEntityManagerFactory.close();
        }
    }

    public List getEvents(Long userId) {
        parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
        parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
        try {
            Query query = parakamaniEntityManager.createQuery("SELECT event FROM Event event WHERE eventId = :userId", Event.class);
            query.setParameter("userId", userId);
            List<Event> events = query.getResultList();
//            log.info("EventDAO - Events: " + events);
            return events;
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            parakamaniEntityManager.close();
            parakamaniEntityManagerFactory.close();
        }
    }

    public void save(Event event) {
        parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
        parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
        try {
//            log.info("Saving event: " + event);
            entityTransaction.begin();
            parakamaniEntityManager.persist(event);
            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            throw new RuntimeException(e);
        } finally {
            parakamaniEntityManager.close();
            parakamaniEntityManagerFactory.close();
        }
    }

    public void save(UserEvent userEvent){
        parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
        parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
        try {
//            log.info("Saving userEvent: " + userEvent);
            parakamaniEntityManager.getTransaction().begin();
            parakamaniEntityManager.persist(userEvent);
            parakamaniEntityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            parakamaniEntityManager.close();
            parakamaniEntityManagerFactory.close();
        }
    }

    public void updateEvent(Event selectedEvent) {
        parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
        parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
        try {
            parakamaniEntityManager.getTransaction().begin();
            parakamaniEntityManager.merge(selectedEvent);
            parakamaniEntityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            parakamaniEntityManager.close();
            parakamaniEntityManagerFactory.close();
        }
    }

    public void removeEvent(Event selectedEvent) {
        parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
        parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
        try {
            parakamaniEntityManager.getTransaction().begin();
            parakamaniEntityManager.remove(selectedEvent);
            parakamaniEntityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            parakamaniEntityManager.close();
            parakamaniEntityManagerFactory.close();
        }
    }

    public Event find(Long id) {
        parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
        parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
        try {
            return parakamaniEntityManager.find(Event.class, id);
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    public User findUser(Long userId){
        parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
        parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
        try {
            log.info("findUser..");
            User user = parakamaniEntityManager.find(User.class, userId);
            return user;
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            parakamaniEntityManager.close();
            parakamaniEntityManagerFactory.close();
        }
    }

    public List<UserEvent> findAllUserEvents() {
        parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
        parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
        try {
            Query query = parakamaniEntityManager.createQuery("SELECT userEvent FROM UserEvent userEvent", UserEvent.class);
            List<UserEvent> userEvents = query.getResultList();
            for (UserEvent userEvent : userEvents) {
                userEvent.setUser(findUser(userEvent.getUserId()));
                userEvent.setEvent(findEvent(userEvent.getEventId()));
            }
//            log.info("userEvents: " + userEvents.size());
            if (userEvents == null || userEvents.size() < 1) {
                return null;
            }
            return userEvents;
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            parakamaniEntityManager.close();
            parakamaniEntityManagerFactory.close();
        }
    }
}