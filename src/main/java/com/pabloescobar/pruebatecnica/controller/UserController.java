package com.pabloescobar.pruebatecnica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pabloescobar.pruebatecnica.dto.UserDTO;
import com.pabloescobar.pruebatecnica.dto.UserResponseDTO;
import com.pabloescobar.pruebatecnica.models.User;
import com.pabloescobar.pruebatecnica.services.UserService;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // @Autowired
    // private JwtTokenUtil jwtTokenUtil;

    // @Autowired
    // private UserDetailsService userDetailsService;

    @PostMapping("/")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserDTO userDto) {
        UserResponseDTO userResponse = userService.createUser(userDto);

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }
}