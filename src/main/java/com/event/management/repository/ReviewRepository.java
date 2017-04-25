package com.event.management.repository;

import com.event.management.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by gatomulesei on 4/24/2017.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

}
