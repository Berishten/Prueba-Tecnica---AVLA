package com.pabloescobar.pruebatecnica.controller.user;

import java.util.Collections;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pabloescobar.pruebatecnica.DAO.User;
import com.pabloescobar.pruebatecnica.DTO.user.CreateUpdateUserResponseDTO;
import com.pabloescobar.pruebatecnica.DTO.user.UserDTO;
import com.pabloescobar.pruebatecnica.enums.Messages;
import com.pabloescobar.pruebatecnica.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Object> createUsers(@RequestBody @Valid UserDTO userDto) {
        try {
            CreateUpdateUserResponseDTO userResponse = userService.createUser(userDto);
            return ResponseEntity.ok().body(userResponse);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") Long id,
            @RequestBody @Valid UserDTO userDto) throws Exception {
        try {
            logger.info(Messages.UPDATING_USER, userDto);
            return new ResponseEntity<>(userService.updateUser(id, userDto), HttpStatus.OK);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", Messages.USER_NOT_FOUND));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable("id") Long id) throws Exception {
        try {
            User user = userService.getUser(id);
            return ResponseEntity.ok().body(user);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", Messages.USER_NOT_FOUND));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id) throws Exception {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().body(Collections.singletonMap("message", Messages.USER_DELETED));
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", Messages.USER_NOT_FOUND));
        }
    }
}
