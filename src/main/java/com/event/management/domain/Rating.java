package com.event.management.domain;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by gatomulesei on 4/24/2017.
 */
@Entity
public class Rating {

    @Embeddable
    public static class RatingId implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long eventId;

        private Long userId;

        public RatingId(){}

        public RatingId(Long eventId, Long userId){
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
    private Rating.RatingId id = new RatingId();

    private Integer score;

    public RatingId getId() {
        return id;
    }

    public void setId(RatingId id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
