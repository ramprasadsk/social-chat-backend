package com.chatsystem.social.Service;

import com.chatsystem.social.DTO.LoginRequest;
import com.chatsystem.social.DTO.RegisterRequest;
import com.chatsystem.social.DTO.UserResponse;
import com.chatsystem.social.Entity.User;
import com.chatsystem.social.Repository.UserRepository;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    
    @Autowired
    private HttpSession session;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public UserResponse register(RegisterRequest registerRequest) {
        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            return null;
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        String hash = encoder.encode(registerRequest.getPassword());
        user.setPassword(hash);
        User saved = userRepository.save(user);
        return new UserResponse(
            saved.getId(),
            saved.getUsername(),
            saved.getEmail()
        );
    }

    public UserResponse login(LoginRequest loginRequest){
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
        if(user.isEmpty()){
            return null;
        }
        User userPresent = user.get();
        if(encoder.matches(loginRequest.getPassword(), userPresent.getPassword())){
            session.setAttribute("user", userPresent);
            return new UserResponse(
                userPresent.getId(),
                userPresent.getUsername(),
                userPresent.getEmail()
            );
        }
        return null;
    }

    public List<UserResponse> getUsers(){
        User currentUser = (User) session.getAttribute("user");
        if(currentUser == null){
            throw new RuntimeException("User not logged in");
        }
        List<User> users = userRepository.findByEmailNot(currentUser.getEmail());
        return users.stream()
                .map(u -> new UserResponse(
                        u.getId(),
                        u.getUsername(),
                        u.getEmail()
                ))
                .toList();
    }

    public String logout(){
        session.invalidate();
        return "Logged out successfully";
    }
}