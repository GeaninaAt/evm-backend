package com.event.management.service;

import com.event.management.domain.*;
import com.event.management.repository.EventRepository;
import com.event.management.repository.RatingRepository;
import com.event.management.repository.ReviewRepository;
import com.event.management.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;


    public Event addEvent(Event event) {
        Event createdEvent = eventRepository.save(event);
        return createdEvent;
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

    public Event addReview(Review review){
        User currentUser = userRepository.findOne(review.getUser().getId());
        Event currentEvent = eventRepository.findOne(review.getEvent().getId());

        if(currentEvent == null){
            LOGGER.error(String.format("No event found with the given ID."));
        }

        if(currentUser == null){
            LOGGER.error(String.format("No user found with the given ID."));
        }

        Review review1 = setProperties(currentUser, currentEvent, review);
        Review savedReview = reviewRepository.save(review1);

        currentEvent.getReviews().add(savedReview);
        return currentEvent;
    }

    private Review setProperties(User user, Event event, Review review){
        review.setEvent(event);
        review.setUser(user);
        return review;
    }

}
