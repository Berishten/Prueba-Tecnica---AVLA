package com.pabloescobar.pruebatecnica.services;

import com.pabloescobar.pruebatecnica.mapper.UserMapper;
import com.pabloescobar.pruebatecnica.dto.UserDTO;
import com.pabloescobar.pruebatecnica.dto.CreateUpdateUserResponseDTO;
import com.pabloescobar.pruebatecnica.models.User;
import com.pabloescobar.pruebatecnica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    // crear user
    public CreateUpdateUserResponseDTO createUser(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        user.getPhones().forEach(phone -> phone.setUser(user));
        userRepository.save(user);

        CreateUpdateUserResponseDTO createUserResponseDTO = userMapper.userToUserResponseDTO(user);
        return createUserResponseDTO;
    }

    // obtener user
    public User getUser(UUID id) throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        return user;
    }

    // actualizar user
    public CreateUpdateUserResponseDTO updateUser(UUID id, UserDTO updateUserRequestDTO)
            throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        return userMapper.userToCreateUpdateUserResponseDTO(userRepository.save(user));
    }

    // eliminar user
    public void deleteUser(UUID id) throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        userRepository.delete(user);
    }

    // get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
