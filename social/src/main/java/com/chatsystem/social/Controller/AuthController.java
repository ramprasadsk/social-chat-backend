package com.chatsystem.social.Controller;

import com.chatsystem.social.DTO.LoginRequest;
import com.chatsystem.social.DTO.RegisterRequest;
import com.chatsystem.social.DTO.UserResponse;
import com.chatsystem.social.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }

    @PostMapping("/login")
    public UserResponse login(@RequestBody LoginRequest loginRequest){
        return userService.login(loginRequest);
    }

    @PostMapping("/logout")
    public String logout(){
        return userService.logout();
    }
}