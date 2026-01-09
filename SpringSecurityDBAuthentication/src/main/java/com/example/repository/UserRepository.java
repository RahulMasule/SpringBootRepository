package com.example.repository;

import com.example.entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MyUser,Long> {

    Optional<MyUser> findByUserName(String userName);

    boolean existsByUserName(String userName);
}

