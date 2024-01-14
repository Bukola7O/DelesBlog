package com.example.delesblog.serviceImpl;

import com.example.delesblog.dto.UserDto;
import com.example.delesblog.enums.Role;
import com.example.delesblog.model.User;
import com.example.delesblog.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(@Lazy UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<User> saveUser(User user) {
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userRepository.findAll();
        return new ResponseEntity<>(userList, HttpStatus.FOUND);
    }

    public ResponseEntity<User> editUserById(Long id, User newUser) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            User user1 = user.get();
            user1.setTitle(newUser.getTitle());
            userRepository.save(user1);
            return new ResponseEntity<>(user1, HttpStatus.OK);
        } else {
            //throw new UsersNotFoundException("Post not Found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<User> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User user1 = user.get();
            return new ResponseEntity<>(user1, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Void> deleteUserById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            User user1 = user.get();
            userRepository.deleteById(user1.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    public ResponseEntity<List<User>> getUserByUserName(String Name) {
        List<User> UserList = userRepository.findUserByTitleIgnoreCaseContains(Name);
        if(UserList.isEmpty()){
            return new ResponseEntity<>(UserList, HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(UserList, HttpStatus.FOUND);
        }
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));
    }

    public User savedUser(UserDto userDto) {
        User user = new ObjectMapper().convertValue(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUserRole(Role.USERS);
        return userRepository.save(user);
    }
}

