package com.example.delesblog.repository;

import com.example.delesblog.model.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {
    List<Users> findUserByTitleIgnoreCaseContains(String title);
}
