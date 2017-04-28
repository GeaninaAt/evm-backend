package com.event.management.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Created by gatomulesei on 4/24/2017.
 */
@Entity
public class Review extends BaseEntity{

    private static final long serialVersionUID = 1L;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JsonBackReference
    private Event event;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JsonBackReference
    private User user;

    @Lob
    private String message;

    public Review(){}

    public Review(User user, Event event, String message){
        this.user = user;
        this.event = event;
        this.message = message;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
