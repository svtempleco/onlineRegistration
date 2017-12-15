package com.dronamraju.svtemple.dao;

import com.dronamraju.svtemple.model.Donation;
import com.dronamraju.svtemple.model.User;
import com.dronamraju.svtemple.model.UserEvent;
import com.dronamraju.svtemple.model.Event;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.*;
import java.util.List;


public class UserDAO {
	private static Log log = LogFactory.getLog(UserDAO.class);

	private static EntityManagerFactory parakamaniEntityManagerFactory;
	private static EntityManager parakamaniEntityManager;

	public void saveEvent(Event event){
		parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
		parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
		try {
//			log.info("Saving event: " + event);
			entityTransaction.begin();
			parakamaniEntityManager.persist(event);
			entityTransaction.commit();
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

	public void updateUserPassword(String email, String newPassword){
		parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
		parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
		try {
			entityTransaction.begin();
			Query query = parakamaniEntityManager.createNativeQuery("UPDATE SVTC_PARAKAMANI_DONOR SET PASSWORD = '" + newPassword + "' WHERE EMAIL = '" + email + "'");
			query.executeUpdate();
			entityTransaction.commit();
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

	public User saveUser(User user){
		parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
		parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
		try {
			log.info("Saving user...");
			User savedUser = null;
			entityTransaction.begin();
			savedUser = parakamaniEntityManager.merge(user);
			entityTransaction.commit();
//			log.info("savedUser: " + savedUser);
			return savedUser;
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

	public User findUser(Long userId){
		parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
		parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
		log.info("findUser..");
		try {
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

	public List<User> findAllUsers(){
		parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
		parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
		try {
			Query query = parakamaniEntityManager.createQuery("SELECT user FROM User user", User.class);
			List<User> users = query.getResultList();
			log.info("users.size(): " + users.size());
			return users;
		} catch (Exception e) {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			throw new RuntimeException(e);
		}
	}

	public User findUser(String email, String password) {
		//log.info("fing user by meail, password...");
		parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
		parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
		try {
			Query query = parakamaniEntityManager.createQuery("SELECT user FROM User user WHERE email = :email and password = :password", User.class);
			query.setParameter("email", email);
			query.setParameter("password", password);
			List<User> users = query.getResultList();
			if (users == null || users.size() < 1) {
				return null;
			}
			User user = users.get(0);
			//log.info("findUser - user: " + user.getFirstName() + ", " + user.getLastName() + ", " + user.getEmail() + ", " + user.getIsAdmin());

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

	public List<UserEvent> findUserEvents(String orderNumber) {
		parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
		parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
		try {
			Query query = parakamaniEntityManager.createQuery("SELECT userEvent FROM UserEvent userEvent WHERE orderNumber = :orderNumber", UserEvent.class);
			query.setParameter("orderNumber", orderNumber);
			List<UserEvent> userEvents = query.getResultList();
			for (UserEvent userEvent : userEvents) {
				userEvent.setUser(findUser(userEvent.getUserId()));
				userEvent.setEvent(findEvent(userEvent.getEventId()));
			}
//			log.info("userEvents: " + userEvents.size());
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

	public List<UserEvent> findUserEvents() {
		parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
		parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
		try {
			Query query = parakamaniEntityManager.createQuery("SELECT userEvent FROM UserEvent userEvent", UserEvent.class);
			List<UserEvent> userEvents = query.getResultList();
			log.info("userEvents: " + userEvents.size());
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

	public List<UserEvent> findUserEvents(Long userId) {
		parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
		parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
		try {
			Query query = parakamaniEntityManager.createQuery("SELECT userEvent FROM UserEvent userEvent WHERE userId = :userId", UserEvent.class);
			query.setParameter("userId", userId);
			List<UserEvent> userEvents = query.getResultList();

			for (UserEvent userEvent : userEvents) {
				userEvent.setUser(this.findUser(userEvent.getUserId()));
				userEvent.setEvent(this.findEvent(userEvent.getEventId()));
			}
			log.info("userEvents: " + userEvents);
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

	public List<Donation> findDonations(Long userId) {
		log.info("findDonations()...");
		parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
		parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
		try {
			Query query = parakamaniEntityManager.createQuery("SELECT donation FROM Donation donation WHERE userId = :userId", Donation.class);
			query.setParameter("userId", userId);
			List<Donation> donations = query.getResultList();

			for (Donation donation : donations) {
				donation.setUser(this.findUser(donation.getUserId()));
				donation.setEvent(this.findEvent(donation.getEventId()));
			}
			log.info("donations: " + donations);
			return donations;
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

	public String findValue(String name) {
		parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
		parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
		try {
			log.info("findValue...");
			Query query = parakamaniEntityManager.createQuery("SELECT value FROM configuration_table configuration WHERE configuration_name = :name", String.class);
			query.setParameter("name", name);
			List<String> values = query.getResultList();
//			log.info("values: " + values);
			if (values != null && values.size() > 0) {
				return values.get(0);
			}
			return null;
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

	public String findPassword(String email) {
		parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
		parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
		try {
			log.info("findPassword by email...");
			Query query = parakamaniEntityManager.createQuery("SELECT password FROM User user WHERE email = :email", String.class);
			query.setParameter("email", email);
			List<String> values = query.getResultList();
			//log.info("values: " + values);
			if (values != null && values.size() > 0) {
				return values.get(0);
			}
			return null;
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

	public void detachUser(User loggedInUser) {
		parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
		parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
		try {
			log.info("detachUser...");
			parakamaniEntityManager.detach(loggedInUser);
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

	public boolean orderNumberExists(String orderNumber) {
		log.info("orderNumberExists...");
		parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
		parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
		try {
//			log.info("orderNumberExists: " + orderNumber);
			Query query = parakamaniEntityManager.createQuery("SELECT orderNumber FROM UserEvent userEvent WHERE orderNumber = :orderNumber", String.class);
			query.setParameter("orderNumber", orderNumber);
			List<String> orderNumbers = query.getResultList();
//			log.info("orderNumbers: " + orderNumbers);
			if (orderNumbers == null || orderNumbers.size() < 1) {
				return false;
			}
			return true;
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
		}
	}

	public void deleteUserEvents(String orderNumber){
		parakamaniEntityManagerFactory = Persistence.createEntityManagerFactory("parakamani-jpa");
		parakamaniEntityManager = parakamaniEntityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = parakamaniEntityManager.getTransaction();
		try {
			log.info("deleteUserEvents..");
			List<UserEvent> userEvents = findUserEvents(orderNumber);
			for (UserEvent userEvent : userEvents) {
				parakamaniEntityManager.remove(userEvent);
			}
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