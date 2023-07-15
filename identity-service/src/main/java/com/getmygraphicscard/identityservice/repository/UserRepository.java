package com.getmygraphicscard.identityservice.repository;

import com.getmygraphicscard.identityservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);



    boolean existsByUsername(String username);
    boolean existsByEmail(String email);


}
