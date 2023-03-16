package com.pabloescobar.pruebatecnica.repository;

import com.pabloescobar.pruebatecnica.models.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Integer> {
}