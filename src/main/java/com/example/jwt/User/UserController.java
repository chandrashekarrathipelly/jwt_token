package com.example.jwt.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.User.dtos.AuthRequest;
import com.example.jwt.User.dtos.AuthResponse;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("auth/users/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return userService.login(authRequest);
    }

    @PostMapping("auth/users/register")
    public UserEntity createUser(@RequestBody UserEntity user) {
        return userService.createUser(user);
    }

    @GetMapping("/hi")
    public String sayHi(){
        return "hi";
    }
}
