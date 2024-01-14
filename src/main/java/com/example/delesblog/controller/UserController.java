package com.example.delesblog.controller;

import com.example.delesblog.dto.UserDto;
import com.example.delesblog.model.User;
import com.example.delesblog.serviceImpl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.delesblog.utilsc.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class UserController {

    private UserServiceImpl userService;
    private PasswordEncoder passwordEncoder;

    private JwtUtils utils;


    @Autowired
    public UserController( UserServiceImpl userService, PasswordEncoder passwordEncoder) {

        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/save-user")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        return userService.saveUser(user);

    }
    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers(){
        return userService.getAllUsers();
    }
    @PutMapping("/edit-user/{id}")
    public ResponseEntity<User> editUsersById(@PathVariable Long id, @RequestBody User newUser) {
        return userService.editUserById(id, newUser);
    }
    @GetMapping ("/get-user/{id}")
    public ResponseEntity<User> getUserById (@PathVariable Long id) {
        return userService.getUserById(id);
    }
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }
    @GetMapping("/get-user-title/{name}")
    public ResponseEntity<List<User>> getUserByUserName(@PathVariable String name){
        return userService.getUserByUserName(name);
    }
    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> signUp(@RequestBody UserDto userDto) {
        User user = userService.savedUser(userDto);
        UserDto userDto1 = new ObjectMapper().convertValue(user, UserDto.class);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);

    }
    @PostMapping("/login")
    public ResponseEntity<String> logInUser(@RequestBody UserDto userDto){
        UserDetails user = userService.loadUserByUsername(userDto.getUsername());
        if(passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            String jwtToken = utils.createJwt.apply(user);
            return new ResponseEntity<>(jwtToken, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("Username and Password is incorrect", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/dashboard")
    public String dashboard() {
        return "Welcome to your Dashboard";

    }

}
