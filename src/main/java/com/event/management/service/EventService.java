package com.event.management.service;

import com.event.management.domain.Event;
import com.event.management.domain.Rating;
import com.event.management.domain.Review;
import com.event.management.repository.EventRepository;
import com.event.management.repository.RatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gatomulesei on 4/5/2017.
 */
@Service
@Transactional
public class EventService {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(EventService.class);

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RatingRepository ratingRepository;


    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event getEvent(Long eventId) {
        return eventRepository.findOne(eventId);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public void deleteEvent(Long eventId) {
        eventRepository.delete(eventId);
    }

    public Event rateEvent(Long eventId, Long userId, Integer score){
        Event targetEvent = eventRepository.findOne(eventId);

        Rating rating = new Rating();
        rating.setScore(score);
        Rating.RatingId ratingId = new Rating.RatingId(eventId, userId);
        rating.setId(ratingId);

        targetEvent.setRating(rating);
        targetEvent.setTotalScore(computeTotalScore(targetEvent));
        Event ratedEvent = eventRepository.save(targetEvent);
        return ratedEvent;
    }

    private Float computeTotalScore(Event event) {
        List<Rating> eventRatings = ratingRepository.findByIdEventId(event.getId());
        int totalRatings = eventRatings.size();
        Float totalScore = null;
        if (totalRatings > 0) {
            Float totalRatingSum = 0f;
            for (Rating rating : eventRatings) {
                totalRatingSum = totalRatingSum + rating.getScore();
            }
            totalScore = totalRatingSum / totalRatings;
            event.setTotalScore(totalRatingSum / totalRatings);
        } else {
            event.setTotalScore(0f);
        }
        return totalScore;
    }

    public Event reviewEvent(Long eventId, Long userId, String text){
        Event targetEvent = eventRepository.findOne(eventId);

        Review review = new Review();
        review.setText(text);
        Review.ReviewId reviewId = new Review.ReviewId(eventId, userId);
        review.setId(reviewId);

        targetEvent.setReview(review);
        Event reviewedEvent = eventRepository.save(targetEvent);
        return reviewedEvent;
    }
}
