package com.shoppingapp.model;

import lombok.Data;

import java.time.LocalDate;


@Data
public class UserDto {

    private String loginId;

    private String email;

    private String firstName;

    private String lastName;

    private String password;

    private String confirmPassword;

    private LocalDate dateOfBirth;

    private String gender;

    private String contactNumber;
    private String role = "ROLE_CUSTOMER";
}
