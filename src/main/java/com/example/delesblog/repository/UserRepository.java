package com.example.delesblog.repository;

import com.example.delesblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUserByTitleIgnoreCaseContains(String title);

   Optional<UserDetails> findUserByUsername(String username);
}
