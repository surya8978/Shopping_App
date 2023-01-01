package com.shoppingapp.repository;

import com.shoppingapp.model.document.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends MongoRepository<Users,String> {
    List<Users> findByEmail(String email);


}
