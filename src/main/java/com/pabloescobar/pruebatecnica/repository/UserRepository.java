package com.pabloescobar.pruebatecnica.repository;

import com.pabloescobar.pruebatecnica.models.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}