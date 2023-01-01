package com.shoppingapp.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shoppingapp.exceptions.InvalidPasswordException;
import com.shoppingapp.model.UserStatus;
import com.shoppingapp.model.document.Users;
import com.shoppingapp.repository.UserRepository;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;


    @Test
    void testRegisterUserValid() {
        Users users = new Users();
        users.setContactNumber("42");
        users.setDateOfBirth(LocalDate.ofEpochDay(1L));
        users.setEmail("testname.test@sample.com");
        users.setFirstName("testname");
        users.setGender("Gender");
        users.setLastName("test");
        users.setLoginId("42");
        users.setPassword("testpass");
        users.setRole("Role");
        when(userRepository.save((Users) any())).thenReturn(users);
        when(userRepository.findByEmail((String) any())).thenReturn(new ArrayList<>());
        when(userRepository.findById((String) any())).thenReturn(Optional.empty());

        Users users1 = new Users();
        users1.setContactNumber("42");
        users1.setDateOfBirth(LocalDate.ofEpochDay(1L));
        users1.setEmail("testname.test@sample.com");
        users1.setFirstName("testname");
        users1.setGender("Gender");
        users1.setLastName("test");
        users1.setLoginId("42");
        users1.setPassword("testpass");
        users1.setRole("Role");
        UserStatus actualRegisterUserResult = userServiceImpl.registerUser(users1);
        assertEquals("42", actualRegisterUserResult.getLoginId());
        assertEquals("User Creation Successful", actualRegisterUserResult.getMessage());
        verify(userRepository).save((Users) any());
        verify(userRepository).findByEmail((String) any());
        verify(userRepository).findById((String) any());
    }

    @Test
    void testRegisterUserInValid() {
        when(userRepository.save((Users) any())).thenThrow(new UsernameNotFoundException("User Creation Successful"));
        when(userRepository.findByEmail((String) any()))
                .thenThrow(new UsernameNotFoundException("User Creation Successful"));
        when(userRepository.findById((String) any()))
                .thenThrow(new UsernameNotFoundException("User Creation Successful"));

        Users users = new Users();
        users.setContactNumber("42");
        users.setDateOfBirth(LocalDate.ofEpochDay(1L));
        users.setEmail("testname.test@sample.com");
        users.setFirstName("testname");
        users.setGender("Gender");
        users.setLastName("test");
        users.setLoginId("42");
        users.setPassword("testpass");
        users.setRole("Role");
        assertThrows(UsernameNotFoundException.class, () -> userServiceImpl.registerUser(users));
        verify(userRepository).findByEmail((String) any());
    }

    @Test
    void testFindUserByLoginIdValid() {
        Users users = new Users();
        users.setContactNumber("42");
        users.setDateOfBirth(LocalDate.ofEpochDay(1L));
        users.setEmail("testname.test@sample.com");
        users.setFirstName("testname");
        users.setGender("Gender");
        users.setLastName("test");
        users.setLoginId("42");
        users.setPassword("testpass");
        users.setRole("Role");
        Optional<Users> ofResult = Optional.of(users);
        when(userRepository.findById((String) any())).thenReturn(ofResult);
        UserDetails actualFindUserByLoginIdResult = userServiceImpl.findUserByLoginId("42", "testpass");
        assertEquals("42", actualFindUserByLoginIdResult.getUsername());
        assertEquals("Role", actualFindUserByLoginIdResult.getPassword());
        verify(userRepository).findById((String) any());
    }


    @Test
    void testFindUserByLoginIdInValid() {
        Users users = mock(Users.class);
        when(users.getLoginId()).thenThrow(new UsernameNotFoundException("testpass"));
        when(users.getRole()).thenThrow(new UsernameNotFoundException("testpass"));
        when(users.getPassword()).thenReturn("testpass");
        users.setContactNumber("42");
        users.setDateOfBirth(LocalDate.ofEpochDay(1L));
        users.setEmail("testname.test@sample.com");
        users.setFirstName("testname");
        users.setGender("Gender");
        users.setLastName("test");
        users.setLoginId("42");
        users.setPassword("testpass");
        users.setRole("Role");
        Optional<Users> ofResult = Optional.of(users);
        when(userRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(UsernameNotFoundException.class, () -> userServiceImpl.findUserByLoginId("42", "testpass"));
        verify(userRepository).findById((String) any());

    }


    @Test
    void testForgotPasswordValid() {
        Users users = new Users();
        users.setContactNumber("42");
        users.setDateOfBirth(LocalDate.ofEpochDay(1L));
        users.setEmail("testname.test@sample.com");
        users.setFirstName("testname");
        users.setGender("Gender");
        users.setLastName("test");
        users.setLoginId("42");
        users.setPassword("testpass");
        users.setRole("Role");
        Optional<Users> ofResult = Optional.of(users);

        Users users1 = new Users();
        users1.setContactNumber("42");
        users1.setDateOfBirth(LocalDate.ofEpochDay(1L));
        users1.setEmail("testname.test@sample.com");
        users1.setFirstName("testname");
        users1.setGender("Gender");
        users1.setLastName("test");
        users1.setLoginId("42");
        users1.setPassword("testpass");
        users1.setRole("Role");
        when(userRepository.save((Users) any())).thenReturn(users1);
        when(userRepository.findById((String) any())).thenReturn(ofResult);

        Users users2 = new Users();
        users2.setContactNumber("42");
        users2.setDateOfBirth(LocalDate.ofEpochDay(1L));
        users2.setEmail("testname.test@sample.com");
        users2.setFirstName("testname");
        users2.setGender("Gender");
        users2.setLastName("test");
        users2.setLoginId("42");
        users2.setPassword("testpass");
        users2.setRole("Role");
        UserStatus actualForgotPasswordResult = userServiceImpl.forgotPassword(users2);
        assertEquals("42", actualForgotPasswordResult.getLoginId());
        assertEquals("Password Changed Successfully", actualForgotPasswordResult.getMessage());
        verify(userRepository).save((Users) any());
        verify(userRepository).findById((String) any());
    }

    @Test
    void testForgotPasswordInValid() {
        Users users = new Users();
        users.setContactNumber("42");
        users.setDateOfBirth(LocalDate.ofEpochDay(1L));
        users.setEmail("testname.test@sample.com");
        users.setFirstName("testname");
        users.setGender("Gender");
        users.setLastName("test");
        users.setLoginId("42");
        users.setPassword("testpass");
        users.setRole("Role");
        Optional<Users> ofResult = Optional.of(users);
        when(userRepository.save((Users) any())).thenThrow(new InvalidPasswordException());
        when(userRepository.findById((String) any())).thenReturn(ofResult);

        Users users1 = new Users();
        users1.setContactNumber("42");
        users1.setDateOfBirth(LocalDate.ofEpochDay(1L));
        users1.setEmail("testname.test@sample.com");
        users1.setFirstName("testname");
        users1.setGender("Gender");
        users1.setLastName("test");
        users1.setLoginId("42");
        users1.setPassword("testpass");
        users1.setRole("Role");
        assertThrows(InvalidPasswordException.class, () -> userServiceImpl.forgotPassword(users1));
        verify(userRepository).save((Users) any());
        verify(userRepository).findById((String) any());
    }

    @Test
    void testUpdateUserValid() {
        Users users = new Users();
        users.setContactNumber("42");
        users.setDateOfBirth(LocalDate.ofEpochDay(1L));
        users.setEmail("testname.test@sample.com");
        users.setFirstName("testname");
        users.setGender("Gender");
        users.setLastName("test");
        users.setLoginId("42");
        users.setPassword("testpass");
        users.setRole("Role");
        Optional<Users> ofResult = Optional.of(users);

        Users users1 = new Users();
        users1.setContactNumber("42");
        users1.setDateOfBirth(LocalDate.ofEpochDay(1L));
        users1.setEmail("testname.test@sample.com");
        users1.setFirstName("testname");
        users1.setGender("Gender");
        users1.setLastName("test");
        users1.setLoginId("42");
        users1.setPassword("testpass");
        users1.setRole("Role");
        when(userRepository.save((Users) any())).thenReturn(users1);
        when(userRepository.findById((String) any())).thenReturn(ofResult);

        Users users2 = new Users();
        users2.setContactNumber("42");
        users2.setDateOfBirth(LocalDate.ofEpochDay(1L));
        users2.setEmail("testname.test@sample.com");
        users2.setFirstName("testname");
        users2.setGender("Gender");
        users2.setLastName("test");
        users2.setLoginId("42");
        users2.setPassword("testpass");
        users2.setRole("Role");
        UserStatus actualUpdateUserResult = userServiceImpl.updateUser(users2);
        assertEquals("42", actualUpdateUserResult.getLoginId());
        assertEquals("Password Changed Successfully", actualUpdateUserResult.getMessage());
        verify(userRepository).save((Users) any());
        verify(userRepository).findById((String) any());
    }

    @Test
    void testUpdateUserInValid() {
        Users users = new Users();
        users.setContactNumber("42");
        users.setDateOfBirth(LocalDate.ofEpochDay(1L));
        users.setEmail("testname.test@sample.com");
        users.setFirstName("testname");
        users.setGender("Gender");
        users.setLastName("test");
        users.setLoginId("42");
        users.setPassword("testpass");
        users.setRole("Role");
        Optional<Users> ofResult = Optional.of(users);
        when(userRepository.save((Users) any())).thenThrow(new InvalidPasswordException());
        when(userRepository.findById((String) any())).thenReturn(ofResult);

        Users users1 = new Users();
        users1.setContactNumber("42");
        users1.setDateOfBirth(LocalDate.ofEpochDay(1L));
        users1.setEmail("testname.test@sample.com");
        users1.setFirstName("testname");
        users1.setGender("Gender");
        users1.setLastName("test");
        users1.setLoginId("42");
        users1.setPassword("testpass");
        users1.setRole("Role");
        assertThrows(InvalidPasswordException.class, () -> userServiceImpl.updateUser(users1));
        verify(userRepository).save((Users) any());
        verify(userRepository).findById((String) any());
    }



    @Test
    void testFindAllUsersValid() {
        ArrayList<Users> usersList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(usersList);
        List<Users> actualFindAllUsersResult = userServiceImpl.findAllUsers();
        assertSame(usersList, actualFindAllUsersResult);
        assertTrue(actualFindAllUsersResult.isEmpty());
        verify(userRepository).findAll();
    }


    @Test
    void testFindAllUsersInValid() {
        when(userRepository.findAll()).thenThrow(new InvalidPasswordException());
        assertThrows(InvalidPasswordException.class, () -> userServiceImpl.findAllUsers());
        verify(userRepository).findAll();
    }


    @Test
    void testLoadUserByUsernameValid() {
        Users users = new Users();
        users.setContactNumber("42");
        users.setDateOfBirth(LocalDate.ofEpochDay(1L));
        users.setEmail("testname.test@sample.com");
        users.setFirstName("testname");
        users.setGender("Gender");
        users.setLastName("test");
        users.setLoginId("42");
        users.setPassword("testpass");
        users.setRole("Role");
        Optional<Users> ofResult = Optional.of(users);
        when(userRepository.findById((String) any())).thenReturn(ofResult);
        UserDetails actualLoadUserByUsernameResult = userServiceImpl.loadUserByUsername("42");
        assertEquals("42", actualLoadUserByUsernameResult.getUsername());
        assertEquals("testpass", actualLoadUserByUsernameResult.getPassword());
        verify(userRepository).findById((String) any());
    }


    @Test
    void testLoadUserByUsernameInValid() {
        when(userRepository.findById((String) any())).thenReturn(Optional.empty());
        Users users = mock(Users.class);
        when(users.getLoginId()).thenReturn("42");
        when(users.getPassword()).thenReturn("testpass");
        users.setContactNumber("42");
        users.setDateOfBirth(LocalDate.ofEpochDay(1L));
        users.setEmail("testname.test@sample.com");
        users.setFirstName("testname");
        users.setGender("Gender");
        users.setLastName("test");
        users.setLoginId("42");
        users.setPassword("testpass");
        users.setRole("Role");
        assertThrows(UsernameNotFoundException.class, () -> userServiceImpl.loadUserByUsername("42"));
        verify(userRepository).findById((String) any());

    }


    @Test
    void testGetuserByloginIdValid() {
        Users users = new Users();
        users.setContactNumber("42");
        users.setDateOfBirth(LocalDate.ofEpochDay(1L));
        users.setEmail("testname.test@sample.com");
        users.setFirstName("testname");
        users.setGender("Gender");
        users.setLastName("test");
        users.setLoginId("42");
        users.setPassword("testpass");
        users.setRole("Role");
        Optional<Users> ofResult = Optional.of(users);
        when(userRepository.findById((String) any())).thenReturn(ofResult);
        assertSame(users, userServiceImpl.getuserByloginId("42"));
        verify(userRepository).findById((String) any());
    }

    @Test
    void testGetuserByloginIdInValid() {
        when(userRepository.findById((String) any())).thenThrow(new InvalidPasswordException());
        assertThrows(InvalidPasswordException.class, () -> userServiceImpl.getuserByloginId("42"));
        verify(userRepository).findById((String) any());
    }
}

