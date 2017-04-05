package com.event.management.repository;

import com.event.management.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by gatomulesei on 4/5/2017.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
