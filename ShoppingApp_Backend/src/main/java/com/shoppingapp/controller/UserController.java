package com.shoppingapp.controller;


import com.shoppingapp.model.document.Users;
import com.shoppingapp.model.UserDto;
import com.shoppingapp.model.LoginResponse;
import com.shoppingapp.model.UserStatus;
import com.shoppingapp.security.JWT.JwtUtil;
import com.shoppingapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping("/shopping")
public class UserController {

    UserService userService;

    JwtUtil jwtUtil;


    @Autowired
    public UserController(UserService userService,JwtUtil jwtUtil){
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    ResponseEntity<?> registerUser(@RequestBody UserDto userDto){
        log.info(String.valueOf(userDto));
        Users user = new Users();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        if(userDto.getPassword().equals(userDto.getConfirmPassword())){
            user.setPassword(userDto.getPassword());
        } else{
            return new ResponseEntity<>("Password did not match..!!", HttpStatus.NOT_ACCEPTABLE);
        }
        user.setLoginId(userDto.getLoginId());
        user.setEmail(userDto.getEmail());
        user.setContactNumber(userDto.getContactNumber());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setGender(userDto.getGender());
        UserStatus userStatus = userService.registerUser(user);
        if (userStatus.getLoginId() != null){
            log.info("Exiting into Register method :" + userStatus);
            return new ResponseEntity<>(userStatus, HttpStatus.CREATED);
        }else if (userStatus.getMessage() == "id"){
            log.info("Exiting into Register method : User Creation Unsuccessful");
            return new ResponseEntity<>("User Creation Unsuccessful :Userid Already exists", HttpStatus.NOT_ACCEPTABLE);
        }else{
            log.info("Exiting into Register method : User Creation Unsuccessful");
            return new ResponseEntity<>("User Creation Unsuccessful :UserEmail Already exists", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping(value = "/login")
    ResponseEntity<LoginResponse> loginUser(@RequestBody UserDto userDto){
        log.info("Entering into loginUser method ");
        String userLoginId = userDto.getLoginId();
        String enteredPassword = userDto.getPassword();
        UserDetails userDetails = userService.findUserByLoginId(userLoginId,enteredPassword);
        log.info("Exiting from loginUser method ");
        return new ResponseEntity<>(new LoginResponse(jwtUtil.generateToken(userDetails),userDto.getLoginId(),userDetails.getPassword()), HttpStatus.CREATED);
    }

    @GetMapping("/{loginId}/forgotpassword")
    ResponseEntity<?> forgotPassword(@RequestBody UserDto userDto){
        Users user = new Users();
        user.setLoginId(userDto.getLoginId());
        user.setEmail(userDto.getEmail());
        user.setContactNumber(userDto.getContactNumber());
        user.setPassword(userDto.getPassword());

        UserStatus userStatus = userService.forgotPassword(user);
        if(userStatus.getLoginId() != null){
            log.info("Exiting from forgotPassword method");
            return new ResponseEntity<>(userStatus,HttpStatus.OK);
        }else {
            log.info("Exiting from forgotPassword method");
            return new ResponseEntity<>("Email Or ContactNumber doesn't Match",HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/{loginId}/updatepassword")
    ResponseEntity<?> updatePassword(@RequestHeader("Authorization") String token,
                                     @RequestBody UserDto userDto,
                                     @PathVariable String loginId){
        log.info("Entering into updatePassword ");

        Users user = new Users();
        user.setLoginId(loginId);
        user.setPassword(userDto.getPassword());
        if (null!= token && jwtUtil.validateToken(token)) {
            UserStatus userStatus = userService.updateUser(user);
            log.info("Exiting from updatePassword ");
            return new ResponseEntity<>(userStatus, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("tokenExpired", HttpStatus.FORBIDDEN);
        }

    }

//    @GetMapping(value = "/validate")
//    public ResponseEntity<ValidationResponse> getValidity(@RequestHeader("Authorization") String token) {
//        log.info("Entering getValidity controller method!!!");
//        log.info("Token: {}",token);
////        String localToken = token.substring(7);
//
//        ValidationResponse response = new ValidationResponse();
//        if (null!= token && jwtUtil.validateToken(token)) {
//            response.setValid(true);
//        }
//        log.info(response.toString());
//        log.info("Exiting getValidity controller method!!!");
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

//    //Get User
//    @PostMapping("/users/all")
//    public ResponseEntity<List<Users>> findAllUsers(HttpServletRequest httpServletRequest){
//        log.info("Entering into findAllUsers method ");
////        String localToken = httpServletRequest.getHeader("Authorization");
////        if(jwtUtil.validateToken(localToken)){
//            log.info("Exiting from findAllUsers method ");
//            return new ResponseEntity<>(userService.findAllUsers(),HttpStatus.OK);
////        }else {
////            log.info("Exiting from findAllUsers method ");
////            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.FORBIDDEN);
//        }



}
