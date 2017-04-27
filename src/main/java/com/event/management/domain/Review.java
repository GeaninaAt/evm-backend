package com.event.management.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by gatomulesei on 4/24/2017.
 */
@Entity
public class Review {

    @Embeddable
    public static class ReviewId implements Serializable{

        private static final long serialVersionUID = 1L;

        private Long eventId;
        private Long userId;

        public ReviewId(){}

        public ReviewId(Long eventId, Long userId){
            this.eventId = eventId;
            this.userId = userId;
        }

        public Long getEventId() {
            return eventId;
        }

        public void setEventId(Long eventId) {
            this.eventId = eventId;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

    }

    @EmbeddedId
    private ReviewId id = new ReviewId();

    @Lob
    private String text;

    public ReviewId getId() {
        return id;
    }

    public void setId(ReviewId id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
