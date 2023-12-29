package com.example.delesblog.repository;

import com.example.delesblog.model.Comments;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByUserId(Long PostId);

    @Transactional
    void deleteByUserId(Long Tutorial);

    List<Comments> findByContentIgnoringCaseContaining(String content);
}
