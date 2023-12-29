package com.example.delesblog.controller;

import com.example.delesblog.exception.UsersNotFoundException;
import com.example.delesblog.model.Users;
import com.example.delesblog.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/save-user")
    public ResponseEntity<Users> saveUser(@RequestBody Users user){
        return userService.saveUser(user);

    }
    @GetMapping("/all-users")
    public ResponseEntity<List<Users>> getAllUsers(){
        return userService.getAllUsers();
    }
    @PutMapping("/edit-user/{id}")
    public ResponseEntity<Users> editUsersById(@PathVariable Long id, @RequestBody Users newUser) {
       return userService.editUserById(id, newUser);
    }
    @GetMapping ("/get-user/{id}")
        public ResponseEntity<Users> getUserById (@PathVariable Long id) {
        return userService.getUserById(id);
        }
        @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
        }
        @GetMapping("/get-user-title/{title}")
    public ResponseEntity<List<Users>> getUserByUserTitle(@PathVariable String title){
        return userService.getUserByUserTitle(title);
        }
}
