package com.dronamraju.svtemple.dao;


import com.dronamraju.svtemple.model.AnnualMembership;
import com.dronamraju.svtemple.model.User;
import com.dronamraju.svtemple.model.Event;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.*;

import java.util.List;

/**
 * Created by mdronamr on 05/11/17.
 */
public class ParakamaniDAO {

    private static Log log = LogFactory.getLog(ParakamaniDAO.class);
    private static EntityManagerFactory parakamaniEntityManagerFactory;
    private static EntityManager parakamaniEntityManager;

    public User findUser(Long userId){
        parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
        parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
        try {
            log.info("findUser..");
            return parakamaniEntityManager.find(User.class, userId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            parakamaniEntityManager.close();
            parakamaniEntityManagerFactory.close();
        }
    }

    public List<User> getUsers() {
        parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
        parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
        try {
            Query query = parakamaniEntityManager.createQuery("SELECT user FROM User user", User.class);
            List<User> users = query.getResultList();
//            log.info("ParakamaniDAO - Users: " + users);
            return users;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            parakamaniEntityManager.close();
            parakamaniEntityManagerFactory.close();
        }
    }

    public List<AnnualMembership> findAnnualMemberships() {
        parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
        parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
        try {
            Query query = parakamaniEntityManager.createQuery("SELECT annualMembership FROM AnnualMembership annualMembership", AnnualMembership.class);
            List<AnnualMembership> annualMemberships = query.getResultList();
            log.info("ParakamaniDAO - annualMemberships: " + annualMemberships);
            return annualMemberships;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            parakamaniEntityManager.close();
            parakamaniEntityManagerFactory.close();
        }
    }

    public List<Event> getEvents() {
        parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
        parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
        try {
            Query query = parakamaniEntityManager.createQuery("SELECT event FROM Event event", Event.class);
            log.info("query: " + query);
            List<Event> events = query.getResultList();
            log.info("ParakamaniDAO - events.size(): " + events.size());
            return events;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            parakamaniEntityManager.close();
            parakamaniEntityManagerFactory.close();
        }
    }

    public void save(User user) {
        parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
        parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
        try {
//            log.info("Saving user: " + user);
            entityTransaction.begin();
            parakamaniEntityManager.persist(user);
            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            throw new RuntimeException(e);
        } finally {
            parakamaniEntityManager.close();
            parakamaniEntityManagerFactory.close();
        }
    }

    public void updateUser(User selectedUser) {
        parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
        parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
        try {
            parakamaniEntityManager.getTransaction().begin();
            parakamaniEntityManager.merge(selectedUser);
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

    public void removeUser(User selectedUser) {
        parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
        parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
        try {
            parakamaniEntityManager.getTransaction().begin();
            parakamaniEntityManager.remove(selectedUser);
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

    public User find(Long id) {
        parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
        parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
        try {
            return parakamaniEntityManager.find(User.class, id);
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