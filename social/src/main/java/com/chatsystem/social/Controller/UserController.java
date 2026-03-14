package com.chatsystem.social.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatsystem.social.Entity.User;
import com.chatsystem.social.Repository.UserRepository;

@RestController
@RequestMapping("/test")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String test() {

        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@test.com");
        user.setPassword("123");

        userRepository.save(user);

        return "saved";
    }
    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    @Autowired
    MongoTemplate mongoTemplate;

    @GetMapping("/dbname")
    public String dbName(){
        return mongoTemplate.getDb().getName();
    }
}