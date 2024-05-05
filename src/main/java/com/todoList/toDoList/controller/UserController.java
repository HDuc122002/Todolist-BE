package com.todoList.toDoList.controller;

import com.todoList.toDoList.entity.Users;
import com.todoList.toDoList.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    @GetMapping("")
//    public ResponseEntity<?> getAllUser(){
//        try {
//
//            userService.getAllUser();
//            return ResponseEntity.ok().build();
//        }catch (Exception e){
//            return ResponseEntity.ok().body(e.getMessage());
//        }
//    }

    @GetMapping("")
    public List<Users> getAllUser(){
        return userService.getAllUser();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody Users user){
        try {
            System.out.println(user.toString());
            userService.createUser(user);
            return ResponseEntity.ok().body("Created User successfully");
        }catch (Exception e){
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@RequestBody Users updateUser,
                                        @PathVariable Long userId){
        try {
            userService.updateUser(userId,updateUser);
            return ResponseEntity.ok().body("User updated successfully");
        }catch (Exception e){
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok().body("deleted user successfully");
        }catch (Exception e){
            return ResponseEntity.ok().body(e.getMessage());
        }
    }
}
