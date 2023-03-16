package com.pabloescobar.pruebatecnica.services;

import com.pabloescobar.pruebatecnica.mapper.UserMapper;
import com.pabloescobar.pruebatecnica.dto.UserDTO;
import com.pabloescobar.pruebatecnica.dto.UserResponseDTO;
import com.pabloescobar.pruebatecnica.models.Phone;
import com.pabloescobar.pruebatecnica.models.User;
import com.pabloescobar.pruebatecnica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    // escribe create user
    public UserResponseDTO createUser(UserDTO userDTO) {
        User user = User.builder()
            .id(UUID.randomUUID())
            .name(userDTO.getName())
            .email(userDTO.getEmail())
            .password(userDTO.getPassword())
            .phones(userDTO.getPhones())
            .created(new Date())
            .modified(new Date())
        .build();
        userRepository.save(user);

        // Mapping
        UserResponseDTO createUserResponseDTO = userMapper.userToUserResponseDTO(user);
        return createUserResponseDTO;
    }

    // obtener user (GET)
    public User getUser(UUID id) throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        return user;
    }

    // actualizar user (PUT)
    public User updateUser(UUID id, UserDTO updateUserRequestDTO)
            throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        user.setName(updateUserRequestDTO.getName());
        user.setEmail(updateUserRequestDTO.getEmail());
        user.setPassword(updateUserRequestDTO.getPassword());
        user.setPhones(updateUserRequestDTO.getPhones());
        return userRepository.save(user);
    }

    // eliminar user (DELETE)
    public void deleteUser(UUID id) throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        userRepository.delete(user);
    }

    // get all users (GET)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // crear phone (POST)
    public Phone createPhone(UUID id, CreatePhoneRequestDTO createPhoneRequestDTO)
            throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Phone phone = new Phone();
        phone.setId(UUID.randomUUID());
        phone.setNumber(createPhoneRequestDTO.getNumber());
        phone.setUser(user);
        return phone;
    }

    // actualizar phone (PUT)
    public Phone updatePhone(UUID id, UUID phoneId, UpdatePhoneRequestDTO updatePhoneRequestDTO)
            throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Phone phone = user.getPhones().stream().filter(p -> p.getId().equals(phoneId)).findFirst()
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        phone.setNumber(updatePhoneRequestDTO.getNumber());
        return phone;
    }

    // eliminar phone (DELETE)
    public void deletePhone(UUID id, UUID phoneIdUuid) throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Phone phone = user.getPhones().stream().filter(p -> p.getId().equals(phoneIdUuid)).findFirst()
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        user.getPhones().remove(phone);
    }
}
