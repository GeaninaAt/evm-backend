package com.event.management.service;

import com.event.management.domain.*;
import com.event.management.repository.EventRepository;
import com.event.management.repository.RatingRepository;
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
/*

    @Autowired
    private EventUserRepository eventUserRepository;
*/


    public Event addEvent(Event event) {
        Event createdEvent = eventRepository.save(event);
/*
        //set user list to be added
        Set<EventUser> eventUserSet = new HashSet<>();

        EventUser eventUser;
        List<Long> usersIds = event.getUsersIds();

        for(Long userId : usersIds){
            eventUser = new EventUser();
            User user = userRepository.findOne(userId);
            if(user == null){
                String msg = "Invalid user for userId: " + userId;
                LOGGER.error(msg);
            }
            eventUser.setUser(user);
            eventUser.setEvent(createdEvent);
            eventUserSet.add(eventUser);
        }

        createdEvent.setEventUsers(eventUserSet);*/
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

    /*private List<Long> getListOfUsersIds(Event event) {
        List<Long> usersIds = event.getUsersIds();
        Iterator<Long> iterator = usersIds.iterator();
        while(iterator.hasNext()){
            Long id = iterator.next();
        }
        return usersIds;
    }

    private List<Long> getUsersIds(Event event){
        List<Long> usersIds = new ArrayList<>();
        Set<EventUser> users = event.getEventUsers();

        for(EventUser user : users){
            Long id = user.getUser().getId();
            usersIds.add(id);
        }
        return usersIds;
    }*/
}
