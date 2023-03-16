package com.pabloescobar.pruebatecnica.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pabloescobar.pruebatecnica.dto.UserDTO;
import com.pabloescobar.pruebatecnica.enums.Messages;
import com.pabloescobar.pruebatecnica.exceptions.EmailAlreadyExistsException;
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
    final String REGEX_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$";
    final String REGEX_EMAIL = "^[A-Za-z0-9+_.-]+@(.+)$";

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserDTO userDto) {
        if (!userDto.getEmail().matches(REGEX_EMAIL)) {
            return new ResponseEntity<>(new MessageHandler(Messages.EMAIL_INVALID), HttpStatus.BAD_REQUEST);
        }
        if (!userDto.getPassword().matches(REGEX_PASSWORD)) {
            return new ResponseEntity<>(new MessageHandler(Messages.PASSWORD_INVALID), HttpStatus.BAD_REQUEST);
        }

        try {
            logger.info("Creando usuario: {}", userDto);
            CreateUpdateUserResponseDTO userResponse = userService.createUser(userDto);
            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
        } catch (EmailAlreadyExistsException e) {
            logger.error("Fallo la creación del usuario: {}", userDto);
            return new ResponseEntity<>(new MessageHandler(Messages.EMAIL_ALREADY_EXISTS), HttpStatus.BAD_REQUEST);
        }
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
        if (!userDto.getEmail().matches(REGEX_EMAIL)) {
            return new ResponseEntity<>(new MessageHandler(Messages.EMAIL_INVALID), HttpStatus.BAD_REQUEST);
        }
        if (!userDto.getPassword().matches(REGEX_PASSWORD)) {
            return new ResponseEntity<>(new MessageHandler(Messages.PASSWORD_INVALID), HttpStatus.BAD_REQUEST);
        }
        try {
            logger.info("Actualizando usuario: {}", userDto);
            return new ResponseEntity<>(userService.updateUser(id, userDto), HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("Fallo la actualización del usuario, usuario no encontrado: {}", userDto);
            return new ResponseEntity<>(new MessageHandler(Messages.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
        } catch (EmailAlreadyExistsException e) {
            logger.error("Fallo la actualización del usuario, email en uso: {}", userDto);
            return new ResponseEntity<>(new MessageHandler(Messages.EMAIL_ALREADY_EXISTS), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id) throws Exception {
        try {
            logger.info("Eliminando usuario con id: {}", id);
            userService.deleteUser(id);
            return new ResponseEntity<>(new MessageHandler(Messages.USER_DELETED), HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("Fallo la eliminación del usuario con id: {}", id);
            return new ResponseEntity<>(new MessageHandler(Messages.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }
}
