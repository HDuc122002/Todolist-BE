package com.todoList.toDoList.security.controller;

import com.todoList.toDoList.entity.AuthenticationResponse;
import com.todoList.toDoList.entity.Users;
import com.todoList.toDoList.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${api.prefix}")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

//    @GetMapping("/login")
//    public String getLoginPage(){
//        return "login";
//    }
//
//    @GetMapping("/registration")
//    public String getRegistrationPage(Model model){
//        model.addAttribute("user",new Users());
//        return "register";
//    }
    @PostMapping("/registration")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody Users registerRequest){
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse>  login(@RequestBody Users loginRequest){
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }
}
