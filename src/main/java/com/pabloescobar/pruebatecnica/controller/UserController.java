package com.pabloescobar.pruebatecnica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pabloescobar.pruebatecnica.dto.UserDTO;
import com.pabloescobar.pruebatecnica.enums.Messages;
import com.pabloescobar.pruebatecnica.models.User;
import com.pabloescobar.pruebatecnica.Utils.MessageHandler;
import com.pabloescobar.pruebatecnica.dto.CreateUpdateUserResponseDTO;
import com.pabloescobar.pruebatecnica.services.UserService;

import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<CreateUpdateUserResponseDTO> createUser(@RequestBody @Valid UserDTO userDto) {
        CreateUpdateUserResponseDTO userResponse = userService.createUser(userDto);

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable("id") Long id) throws Exception {
        try {
            User user = userService.getUser(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MessageHandler(Messages.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") Long id,
            @RequestBody @Valid UserDTO userDto) throws Exception {
        try {
            return new ResponseEntity<>(userService.updateUser(id, userDto), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MessageHandler(Messages.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id) throws Exception {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(new MessageHandler(Messages.USER_DELETED), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new MessageHandler(Messages.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }
}
