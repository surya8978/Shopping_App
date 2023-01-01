package com.shoppingapp.service;

import com.shoppingapp.model.document.Users;
import com.shoppingapp.model.UserStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserStatus registerUser(Users user);

    List<Users> findAllUsers();

    UserDetails findUserByLoginId(String loginId, String enteredPassword);

    UserStatus forgotPassword(Users user);

    UserStatus updateUser(Users user);

    UserDetails loadUserByUsername(String username);


    Users getuserByloginId(String loginId);
}
