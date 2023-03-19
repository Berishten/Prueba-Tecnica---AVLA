package com.pabloescobar.pruebatecnica.services;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import com.pabloescobar.pruebatecnica.DAO.User;
import com.pabloescobar.pruebatecnica.DTO.user.CreateUpdateUserResponseDTO;
import com.pabloescobar.pruebatecnica.DTO.user.UserDTO;
import com.pabloescobar.pruebatecnica.Utils.JwtUtil;
import com.pabloescobar.pruebatecnica.Utils.UserValidation;
import com.pabloescobar.pruebatecnica.enums.Messages;
import com.pabloescobar.pruebatecnica.mapper.UserMapper;
import com.pabloescobar.pruebatecnica.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    public CreateUpdateUserResponseDTO createUser(UserDTO userDTO) throws ValidationException {
        validateUser(userDTO);
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new ValidationException(Messages.EMAIL_ALREADY_EXISTS);
        }

        User user = userMapper.userDTOToUser(userDTO);
        user.getPhones().forEach(phone -> phone.setUser(user));

        String token = jwtUtil.generateToken(user.getName(), user.getEmail());
        user.setToken(token);

        userRepository.save(user);

        CreateUpdateUserResponseDTO createUserResponseDTO = userMapper.userToUserResponseDTO(user);
        return createUserResponseDTO;
    }

    public User getUser(Long id) throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        return user;
    }

    private void validateUser(UserDTO userDTO) {
        // Validacion del formato del correo y la contraseña
        if (!UserValidation.isValidEmail(userDTO.getEmail())) {
            throw new ValidationException(Messages.EMAIL_INVALID);
        }
        if (!UserValidation.isValidPassword(userDTO.getPassword())) {
            throw new ValidationException(Messages.PASSWORD_INVALID);
        }
    }

    public CreateUpdateUserResponseDTO updateUser(Long id, UserDTO updateUserRequestDTO)
            throws ChangeSetPersister.NotFoundException, ValidationException {
        validateUser(updateUserRequestDTO);
        User user = userRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        if (userRepository.findByEmail(updateUserRequestDTO.getEmail()).isPresent()
                && !user.getEmail().equals(updateUserRequestDTO.getEmail())) {
            throw new ValidationException(Messages.EMAIL_ALREADY_EXISTS);
        }

        userMapper.updateUserFromUserDTO(updateUserRequestDTO, user);
        user.getPhones().forEach(phone -> phone.setUser(user));
        userRepository.save(user);
        return userMapper.userToUserResponseDTO(user);
    }

    public void deleteUser(Long id) throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        userRepository.delete(user);
    }
}
