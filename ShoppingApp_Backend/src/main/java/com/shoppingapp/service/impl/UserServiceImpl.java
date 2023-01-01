package com.shoppingapp.service.impl;

import com.shoppingapp.model.document.Users;
import com.shoppingapp.exceptions.InvalidPasswordException;
import com.shoppingapp.model.UserStatus;
import com.shoppingapp.repository.UserRepository;
import com.shoppingapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserStatus registerUser(Users user) {
        UserStatus userStatus = null;
        List<Users> email = userRepository.findByEmail(user.getEmail());
        Optional<Users> id = userRepository.findById(user.getLoginId());

        if(id.stream().count() > 0){
            userStatus = new UserStatus(null,"id");
        } else if (email.stream().count() > 0) {
            userStatus = new UserStatus(null,"user");
        }else {
            userRepository.save(user);
            userStatus = new UserStatus(user.getLoginId(),"User Creation Successful");
            log.info("User Created Successfully");
        }
       
        return userStatus;
    }

    public UserDetails findUserByLoginId(String loginId,String enteredPassword) {
        Users user = userRepository.findById(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist for the username:"));
        String originalPassword = user.getPassword();
        if(originalPassword.equals(enteredPassword)){
            return new User(user.getLoginId(),user.getRole(),new ArrayList<>());
        }else{
            throw new InvalidPasswordException();
        }
    }

    @Override
    public UserStatus forgotPassword(Users userEnteredDetails) {
        UserStatus userStatus = null;
        Users userOriginalDetails = userRepository.findById(userEnteredDetails.getLoginId())
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist for the username:"));
        if(userEnteredDetails.getEmail().equals(userOriginalDetails.getEmail())
                && userEnteredDetails.getContactNumber().equals(userOriginalDetails.getContactNumber())){

            String enteredPassword = userEnteredDetails.getPassword();
            String originalPassword = userOriginalDetails.getPassword();
            if (!originalPassword.equals(enteredPassword)){
                userOriginalDetails.setPassword(enteredPassword);
            }
            userRepository.save(userOriginalDetails);
             userStatus = new UserStatus(userEnteredDetails.getLoginId(),"Password Changed Successfully");
            return userStatus;
        }else{
            userStatus = new UserStatus(null,"Password Changed UnSuccessfully");
            return  userStatus;
        }


    }

    @Override
    public UserStatus updateUser(Users user) {
        Users userDetails = userRepository.findById(user.getLoginId())
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist for the username:"));
        String enteredPassword = user.getPassword();
        String originalPassword = userDetails.getPassword();
        if (!originalPassword.equals(enteredPassword)){
            userDetails.setPassword(enteredPassword);
        }
        userRepository.save(userDetails);
        UserStatus userStatus = new UserStatus(user.getLoginId(),"Password Changed Successfully");
        log.info("User Created Successfully");
        return userStatus;
    }

    @Override
    public List<Users> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) {
        Users user = userRepository.findById(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist for the username:"));
        return new User(user.getLoginId(),user.getPassword(),new ArrayList<>());
    }



    @Override
    public Users getuserByloginId(String loginId) {
        return userRepository.findById(loginId).orElse(null);
    }
}
