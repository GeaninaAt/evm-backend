package com.event.management.repository;

import com.event.management.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gatomulesei on 4/24/2017.
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByIdEventId(Long eventId);
}
