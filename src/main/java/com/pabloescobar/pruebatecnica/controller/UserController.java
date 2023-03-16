package com.pabloescobar.pruebatecnica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pabloescobar.pruebatecnica.dto.UserDTO;
import com.pabloescobar.pruebatecnica.models.User;
import com.pabloescobar.pruebatecnica.dto.CreateUpdateUserResponseDTO;
import com.pabloescobar.pruebatecnica.services.UserService;

import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<CreateUpdateUserResponseDTO> createUser(@RequestBody @Valid UserDTO userDto) {
        CreateUpdateUserResponseDTO userResponse = userService.createUser(userDto);

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    // obtener user por uuid
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") String id) throws Exception {
        User user = userService.getUser(UUID.fromString(id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // actualizar user por uuid
    @PutMapping("/{id}")
    public ResponseEntity<CreateUpdateUserResponseDTO> updateUser(@PathVariable("id") UUID id, @RequestBody @Valid UserDTO userDto)
            throws Exception {
                CreateUpdateUserResponseDTO userResponse = userService.updateUser(id, userDto);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
