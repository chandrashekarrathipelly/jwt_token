package com.example.jwt.User;

import com.example.jwt.Jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.jwt.User.dtos.AuthRequest;
import com.example.jwt.User.dtos.AuthResponse;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired()
    private PasswordEncoder encoder;

    public UserEntity createUser(UserEntity user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return this.userRepository.save(user);

    }

    public AuthResponse login(AuthRequest authRequest) {
        UserDetails userDetails = this.loadUserByUsername(authRequest.getUserName());
        boolean isSamePassword = this.encoder.matches(authRequest.getPassword(), userDetails.getPassword());
        if (isSamePassword) {
            return AuthResponse
                    .builder()
                    .token(this.jwtService.generateToken(userDetails.getUsername()))
                    .build();
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid User");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "user not found"));
    }

}
