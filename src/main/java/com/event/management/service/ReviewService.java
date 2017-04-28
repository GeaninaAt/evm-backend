package com.event.management.service;

import com.event.management.domain.Event;
import com.event.management.domain.Review;
import com.event.management.repository.EventRepository;
import com.event.management.repository.ReviewRepository;
import com.event.management.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by gatomulesei on 4/24/2017.
 */
@Service
@Transactional
public class ReviewService {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;



    public List<Review> findAllByEvent(Long eventId){
        Event currentEvent = eventRepository.findOne(eventId);
        if(currentEvent == null){
            LOGGER.error(String.format("No event found with the given ID."));
        }

        List<Review> eventReviews = reviewRepository.findByEventId(eventId);
        return eventReviews;
    }

    public void deleteReview(Long reviewId){
        reviewRepository.delete(reviewId);
    }

    public Review getReview(Long reviewId){
        return reviewRepository.findOne(reviewId);
    }

    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }


}
