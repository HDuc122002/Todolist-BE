package com.todoList.toDoList.service;

import com.todoList.toDoList.entity.Users;
import com.todoList.toDoList.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Users> getAllUser(){
        return userRepository.findAll();
    }



    public void createUser(Users user){
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(user.getRole());

        userRepository.save(user);
    }

    @Transactional
    public void updateUser(Long userId, Users updateUser){
        Users existingUser = userRepository.findById(userId)
                .orElseThrow(()->new EntityNotFoundException("User not found by ID: "+userId));

        existingUser.setUsername(updateUser.getUsername());
        existingUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        existingUser.setRole(updateUser.getRole());

        userRepository.save(existingUser);
    }

    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }
}
