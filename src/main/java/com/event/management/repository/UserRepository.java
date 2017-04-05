package com.event.management.repository;

import com.event.management.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by gatomulesei on 4/5/2017.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    User findByUsername(String username);

    User findByEmail(String email);
}
