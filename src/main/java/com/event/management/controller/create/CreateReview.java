package com.event.management.controller.create;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

/**
 * Created by gatomulesei on 4/24/2017.
 */
public class CreateReview {

    @NotNull
    private Long eventId;

    @NotNull
    private String userName;

    @NotNull
    @Lob
    private String text;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
