package com.shoppingapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingapp.model.UserDto;
import com.shoppingapp.model.UserStatus;
import com.shoppingapp.model.document.Users;
import com.shoppingapp.repository.UserRepository;
import com.shoppingapp.security.JWT.JwtUtil;
import com.shoppingapp.service.UserService;
import com.shoppingapp.service.impl.UserServiceImpl;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @Test
    void testLoginUserValid() throws Exception {
        when(userService.findUserByLoginId((String) any(), (String) any()))
                .thenReturn(new User("testnametest", "testpass", new ArrayList<>()));
        when(jwtUtil.generateToken((UserDetails) any())).thenReturn("ABC123");

        UserDto userDto = new UserDto();
        userDto.setConfirmPassword("testpass");
        userDto.setContactNumber("42");
        userDto.setDateOfBirth(null);
        userDto.setEmail("testname.test@sample.com");
        userDto.setFirstName("testname");
        userDto.setGender("Gender");
        userDto.setLastName("test");
        userDto.setLoginId("42");
        userDto.setPassword("testpass");
        userDto.setRole("Role");
        String content = (new ObjectMapper()).writeValueAsString(userDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/shopping/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"authToken\":\"ABC123\",\"loginId\":\"42\",\"role\":\"testpass\"}"));
    }

    @Test
    void testUpdatePasswordValid() throws Exception {
        when(userService.updateUser((Users) any())).thenReturn(new UserStatus("42", "Not all who wander are lost"));
        when(jwtUtil.validateToken((String) any())).thenReturn(true);

        UserDto userDto = new UserDto();
        userDto.setConfirmPassword("testpass");
        userDto.setContactNumber("42");
        userDto.setDateOfBirth(null);
        userDto.setEmail("testname.test@sample.com");
        userDto.setFirstName("testname");
        userDto.setGender("Gender");
        userDto.setLastName("test");
        userDto.setLoginId("42");
        userDto.setPassword("testpass");
        userDto.setRole("Role");
        String content = (new ObjectMapper()).writeValueAsString(userDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/shopping/{loginId}/updatepassword", "42")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"loginId\":\"42\",\"message\":\"Not all who wander are lost\"}"));
    }

    @Test
    void testUpdatePasswordInValid() throws Exception {
        when(userService.updateUser((Users) any())).thenReturn(new UserStatus("42", "Not all who wander are lost"));
        when(jwtUtil.validateToken((String) any())).thenReturn(false);

        UserDto userDto = new UserDto();
        userDto.setConfirmPassword("testpass");
        userDto.setContactNumber("42");
        userDto.setDateOfBirth(null);
        userDto.setEmail("testname.test@sample.com");
        userDto.setFirstName("testname");
        userDto.setGender("Gender");
        userDto.setLastName("test");
        userDto.setLoginId("42");
        userDto.setPassword("testpass");
        userDto.setRole("Role");
        String content = (new ObjectMapper()).writeValueAsString(userDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/shopping/{loginId}/updatepassword", "42")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("tokenExpired"));
    }
    @Test
    void testRegisterUser() {

        UserService userService = mock(UserService.class);
        when(userService.registerUser((Users) any())).thenReturn(new UserStatus());
        UserController userController = new UserController(userService, new JwtUtil());

        UserDto userDto = new UserDto();
        userDto.setConfirmPassword("testpass");
        userDto.setContactNumber("42");
        userDto.setDateOfBirth(LocalDate.ofEpochDay(1L));
        userDto.setEmail("testname.test@sample.com");
        userDto.setFirstName("testname");
        userDto.setGender("Gender");
        userDto.setLastName("test");
        userDto.setLoginId("42");
        userDto.setPassword("testpass");
        userDto.setRole("Role");
        ResponseEntity<?> actualRegisterUserResult = userController.registerUser(userDto);
        assertEquals("User Creation Unsuccessful :UserEmail Already exists", actualRegisterUserResult.getBody());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, actualRegisterUserResult.getStatusCode());
        assertTrue(actualRegisterUserResult.getHeaders().isEmpty());
        verify(userService).registerUser((Users) any());
    }


    @Test
    void testRegisterUser2() {

        UserService userService = mock(UserService.class);
        when(userService.registerUser((Users) any())).thenReturn(new UserStatus("42", "Not all who wander are lost"));
        UserController userController = new UserController(userService, new JwtUtil());
        UserDto userDto = mock(UserDto.class);
        when(userDto.getConfirmPassword()).thenReturn("foo");
        when(userDto.getFirstName()).thenReturn("testname");
        when(userDto.getLastName()).thenReturn("test");
        when(userDto.getPassword()).thenReturn("testpass");
        userDto.setConfirmPassword("testpass");
        userDto.setContactNumber("42");
        userDto.setDateOfBirth(LocalDate.ofEpochDay(1L));
        userDto.setEmail("testname.test@sample.com");
        userDto.setFirstName("testname");
        userDto.setGender("Gender");
        userDto.setLastName("test");
        userDto.setLoginId("42");
        userDto.setPassword("testpass");
        userDto.setRole("Role");
        ResponseEntity<?> actualRegisterUserResult = userController.registerUser(userDto);
        assertEquals("Password did not match..!!", actualRegisterUserResult.getBody());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, actualRegisterUserResult.getStatusCode());
        assertTrue(actualRegisterUserResult.getHeaders().isEmpty());

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
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save((Users) any())).thenReturn(users1);
        when(userRepository.findById((String) any())).thenReturn(ofResult);
        UserServiceImpl userService = new UserServiceImpl(userRepository);
        UserController userController = new UserController(userService, new JwtUtil());

        UserDto userDto = new UserDto();
        userDto.setConfirmPassword("testpass");
        userDto.setContactNumber("42");
        userDto.setDateOfBirth(LocalDate.ofEpochDay(1L));
        userDto.setEmail("testname.test@sample.com");
        userDto.setFirstName("testname");
        userDto.setGender("Gender");
        userDto.setLastName("test");
        userDto.setLoginId("42");
        userDto.setPassword("testpass");
        userDto.setRole("Role");
        ResponseEntity<?> actualForgotPasswordResult = userController.forgotPassword(userDto);
        assertTrue(actualForgotPasswordResult.hasBody());
        assertTrue(actualForgotPasswordResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualForgotPasswordResult.getStatusCode());
        assertEquals("42", ((UserStatus) actualForgotPasswordResult.getBody()).getLoginId());
        assertEquals("Password Changed Successfully", ((UserStatus) actualForgotPasswordResult.getBody()).getMessage());
    }

    @Test
    void testForgotPasswordInValid() {
        Users users = mock(Users.class);
        when(users.getPassword()).thenReturn("testpass");
        when(users.getContactNumber()).thenReturn("foo");
        when(users.getEmail()).thenReturn("testname.test@sample.com");
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
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save((Users) any())).thenReturn(users1);
        when(userRepository.findById((String) any())).thenReturn(ofResult);
        UserServiceImpl userService = new UserServiceImpl(userRepository);
        UserController userController = new UserController(userService, new JwtUtil());

        UserDto userDto = new UserDto();
        userDto.setConfirmPassword("testpass");
        userDto.setContactNumber("42");
        userDto.setDateOfBirth(LocalDate.ofEpochDay(1L));
        userDto.setEmail("testname.test@sample.com");
        userDto.setFirstName("testname");
        userDto.setGender("Gender");
        userDto.setLastName("test");
        userDto.setLoginId("42");
        userDto.setPassword("testpass");
        userDto.setRole("Role");
        ResponseEntity<?> actualForgotPasswordResult = userController.forgotPassword(userDto);
        assertEquals("Email Or ContactNumber doesn't Match", actualForgotPasswordResult.getBody());
        assertEquals(HttpStatus.UNAUTHORIZED, actualForgotPasswordResult.getStatusCode());
        assertTrue(actualForgotPasswordResult.getHeaders().isEmpty());
    }


}

