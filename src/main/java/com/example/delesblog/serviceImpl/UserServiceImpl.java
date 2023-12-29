package com.example.delesblog.serviceImpl;

import com.example.delesblog.exception.UsersNotFoundException;
import com.example.delesblog.model.Users;
import com.example.delesblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<Users> saveUser(Users user) {
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> userList = userRepository.findAll();
        return new ResponseEntity<>(userList, HttpStatus.FOUND);
    }

    public ResponseEntity<Users> editUserById(Long id, Users newUser) {
        Optional<Users> user = userRepository.findById(id);

        if (user.isPresent()) {
            Users user1 = user.get();
            user1.setTitle(newUser.getTitle());
            user1.setDescription(newUser.getDescription());
            userRepository.save(user1);
            return new ResponseEntity<>(user1, HttpStatus.OK);
        } else {
            //throw new UsersNotFoundException("Post not Found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Users> getUserById(Long id) {
        Optional<Users> user = userRepository.findById(id);
        if (user.isPresent()) {
            Users user1 = user.get();
            return new ResponseEntity<>(user1, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Void> deleteUserById(Long id) {
        Optional<Users> user = userRepository.findById(id);

        if (user.isPresent()) {
            Users user1 = user.get();
            userRepository.deleteById(user1.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    public ResponseEntity<List<Users>> getUserByUserTitle(String title) {
        List<Users> usersList= userRepository.findUserByTitleIgnoreCaseContains(title);
        if(usersList.isEmpty()){
            return new ResponseEntity<>(usersList, HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(usersList, HttpStatus.FOUND);
        }
    }
}
