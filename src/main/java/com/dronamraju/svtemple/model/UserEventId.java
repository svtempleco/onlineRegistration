package com.dronamraju.svtemple.model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class UserEventId implements java.io.Serializable {

	private User user;
    private Event event;

	@Override
	public String toString() {
		return "UserEventId{" +
				"user=" + user +
				", event=" + event +
				'}';
	}

	@ManyToOne
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}


}