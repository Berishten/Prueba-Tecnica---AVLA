package com.pabloescobar.pruebatecnica.repository;

import com.pabloescobar.pruebatecnica.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    // metodo crear usuario debe retornar algo asi:
    // {
    //     "id": "229f1d7d-9d1a-4730-af9f-c2c5e16d5050",
    //     "name": "Pablo Escobar",
    //     "email": "beridevs@gmail.com",
    //     "password": "$2a$10$2cQKNSilNlcFAu/.HvwBsestGLB9cHVAkoOpinDD3wNpfPWMhURge",
    //     "phones": [
    //         {
    //             "id": 1,
    //             "user": null
    //         },
    //         {
    //             "id": 2,
    //             "user": null
    //         }
    //     ]
    // }

    @Override
    default <S extends User> S save(S entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    
}