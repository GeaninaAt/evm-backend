package com.event.management.service;

import com.event.management.domain.Review;
import com.event.management.repository.ReviewRepository;
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

    public Review addReview(Review review){
        if(review == null){
            LOGGER.error("Exception while persisting Review object. Object is null.");
            throw new UnsupportedOperationException("Add Review could not be performed.");
        }

        return reviewRepository.save(review);
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
