package com.event.management.repository;

import com.event.management.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gatomulesei on 4/24/2017.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByEventId(Long eventId);
}
