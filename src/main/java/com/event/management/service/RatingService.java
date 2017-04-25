package com.event.management.service;

import com.event.management.domain.Rating;
import com.event.management.repository.RatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by gatomulesei on 4/24/2017.
 */
@Service
@Transactional
public class RatingService {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(RatingService.class);

    @Autowired
    private RatingRepository ratingRepository;

    public Rating addRating(Rating rating){
        if(rating == null){
            LOGGER.error("Exception while persisting Rating object. Object is null.");
            throw new UnsupportedOperationException("Add Rating could not be performed.");
        }
        return ratingRepository.save(rating);
    }
}
