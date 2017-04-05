package com.event.management.repository;

import com.event.management.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by gatomulesei on 4/5/2017.
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
