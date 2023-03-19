package com.pabloescobar.pruebatecnica.repository;

import com.pabloescobar.pruebatecnica.DAO.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Integer> {
}