package com.rahul.repository;

import com.rahul.entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MyUser,Long> {

    Optional<MyUser> findByUserName(String userName);

    boolean existsByUserName(String userName);
}

