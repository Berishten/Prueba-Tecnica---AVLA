package com.pabloescobar.pruebatecnica;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.pabloescobar.pruebatecnica.DTO.phone.PhoneDTO;
import com.pabloescobar.pruebatecnica.DTO.user.CreateUpdateUserResponseDTO;
import com.pabloescobar.pruebatecnica.DTO.user.UserDTO;
import com.pabloescobar.pruebatecnica.mapper.UserMapper;
import com.pabloescobar.pruebatecnica.DAO.Phone;
import com.pabloescobar.pruebatecnica.DAO.User;
import com.pabloescobar.pruebatecnica.repository.UserRepository;
import com.pabloescobar.pruebatecnica.services.UserService;
import com.pabloescobar.pruebatecnica.utils.JwtUtil;
import com.pabloescobar.pruebatecnica.utils.UserValidation;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    @Test
    public void testCreateUser() {
        UserDTO userDTO = UserDTO.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .password("Password12")
                .phones(Collections.singletonList(
                        PhoneDTO.builder().number("123456789").build()))
                .build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .phones(Collections.singletonList(
                        Phone.builder().number("123456789").build()))
                .build();
        when(userMapper.userDTOToUser(userDTO)).thenReturn(user);
        when(jwtUtil.generateToken(anyString(), anyString())).thenReturn("token");
        when(userRepository.save(user)).thenReturn(user);

        CreateUpdateUserResponseDTO responseDTO = CreateUpdateUserResponseDTO.builder()
                .id(1L)
                .created(new Date())
                .modified(new Date())
                .token("token")
                .build();
        when(userMapper.userToUserResponseDTO(user)).thenReturn(responseDTO);

        CreateUpdateUserResponseDTO result = userService.createUser(userDTO);

        verify(userRepository).save(user);
        verify(userMapper).userToUserResponseDTO(user);

        assertNotNull(result.getId());
        assertNotNull(result.getCreated());
        assertNotNull(result.getModified());
        assertEquals("token", result.getToken());

    }

    @Test
    public void testValidPassword() {
        String password = "Abcde12";
        assertTrue(password.matches(UserValidation.PASSWORD_REGEX));
    }

    @Test
    public void testInvalidPasswordNoUppercase() {
        String password = "abcde12";
        assertFalse(password.matches(UserValidation.PASSWORD_REGEX));
    }

    @Test
    public void testInvalidPasswordNoLowercase() {
        String password = "ABCDE12";
        assertFalse(password.matches(UserValidation.PASSWORD_REGEX));
    }

    @Test
    public void testInvalidPasswordNoNumbers() {
        String password = "Abcdef";
        assertFalse(password.matches(UserValidation.PASSWORD_REGEX));
    }

    @Test
    public void testValidEmail() {
        String email = "johndoe@example.com";
        assertTrue(email.matches(UserValidation.EMAIL_REGEX));
    }

    @Test
    public void testInvalidEmailNoUsername() {
        String email = "@example.com";
        assertFalse(email.matches(UserValidation.EMAIL_REGEX));
    }

    @Test
    public void testInvalidEmailNoDomain() {
        String email = "johndoe@";
        assertFalse(email.matches(UserValidation.EMAIL_REGEX));
    }

    @Test
    public void testInvalidEmailInvalidCharacters() {
        String email = "john.doe@example,com";
        assertFalse(email.matches(UserValidation.EMAIL_REGEX));
    }

}
