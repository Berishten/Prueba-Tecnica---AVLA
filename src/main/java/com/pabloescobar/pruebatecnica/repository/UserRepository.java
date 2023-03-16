package com.pabloescobar.pruebatecnica.repository;

import com.pabloescobar.pruebatecnica.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}