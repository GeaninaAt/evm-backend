package com.event.management.controller.create;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

/**
 * Created by gatomulesei on 4/28/2017.
 */
public class CreateReview {

    @NotNull
    private Long eventId;

    @NotNull
    private String userName;

    @NotNull
    @Lob
    private String message;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
