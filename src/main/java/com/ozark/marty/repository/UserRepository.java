package com.ozark.marty.repository;

import com.ozark.marty.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {

    Users findByEmail(String email);

    //Users findByEmail(String email);
}
