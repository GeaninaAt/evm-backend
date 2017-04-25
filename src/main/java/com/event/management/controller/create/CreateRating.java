package com.event.management.controller.create;

import javax.validation.constraints.NotNull;

/**
 * Created by gatomulesei on 4/24/2017.
 */
public class CreateRating {

    @NotNull
    private Long eventId;

    @NotNull
    private String userName;

    @NotNull
    private Integer score;

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
