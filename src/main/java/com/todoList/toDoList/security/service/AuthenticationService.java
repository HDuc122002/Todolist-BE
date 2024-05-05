package com.todoList.toDoList.security.service;

import com.todoList.toDoList.entity.AuthenticationResponse;
import com.todoList.toDoList.entity.Users;
import com.todoList.toDoList.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(Users registerRequest){
        registerRequest.setUsername(registerRequest.getUsername());
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        registerRequest.setRole(registerRequest.getRole());

        registerRequest = userRepository.save(registerRequest);

        String token = jwtService.generateToken(registerRequest);
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse login(Users loginRequest) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

        Users users = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow();
        String token = jwtService.generateToken(users);
        return new AuthenticationResponse(token);
        }
}


