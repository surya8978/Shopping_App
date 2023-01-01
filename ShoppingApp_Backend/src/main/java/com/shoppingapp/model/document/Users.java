package com.shoppingapp.model.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("users")
public class Users {

    @Id
    private String loginId;

    private String firstName;
    private String lastName;
    @Indexed(unique=true)
    private String email;
    private String password;
    private LocalDate dateOfBirth;
    private String contactNumber;
    private String gender;
    private String role = "ROLE_CUSTOMER";



}
