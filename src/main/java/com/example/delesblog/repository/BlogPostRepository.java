package com.example.delesblog.repository;

import com.example.delesblog.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    List<BlogPost> findBlogPostByTitleIgnoreCaseContains(String title);
}
