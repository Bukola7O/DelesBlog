package com.example.delesblog.serviceImpl;

import com.example.delesblog.model.BlogPost;
import com.example.delesblog.repository.BlogPostRepository;
import com.example.delesblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogPostServiceImpl {

    private BlogPostRepository blogPostRepository;

    @Autowired
    public BlogPostServiceImpl(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    public ResponseEntity<BlogPost> saveBlogPost(BlogPost blogPost) {
        blogPostRepository.save(blogPost);
        return new ResponseEntity<>(blogPost, HttpStatus.CREATED);
    }

    public ResponseEntity<List<BlogPost>> getAllBlogPost() {
        List<BlogPost> BlogPostList = blogPostRepository.findAll();
        return new ResponseEntity<>(BlogPostList, HttpStatus.FOUND);
    }

    public ResponseEntity<BlogPost> editBlogPostById(Long id, BlogPost newBlogPost) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);

        if (blogPost.isPresent()) {
            BlogPost blogPost1 = blogPost.get();
            blogPost1.setTitle(newBlogPost.getTitle());
            blogPost1.setDescription(newBlogPost.getDescription());
            blogPostRepository.save(blogPost1);
            return new ResponseEntity<>(blogPost1, HttpStatus.OK);
        } else {
            //throw new UsersNotFoundException("Post not Found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<BlogPost> getBlogPostById(Long id) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);
        if (blogPost.isPresent()) {
            BlogPost blogPost1 = blogPost.get();
            return new ResponseEntity<>(blogPost1, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Void> deleteBlogPostById(Long id) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);

        if (blogPost.isPresent()) {
            BlogPost blogPost1 = blogPost.get();
            blogPostRepository.deleteById(blogPost1.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    public ResponseEntity<List<BlogPost>> getBlogPostByBlogPostTitle(String title) {
        List<BlogPost> blogPostList = blogPostRepository.findBlogPostByTitleIgnoreCaseContains(title);
        if(blogPostList.isEmpty()){
            return new ResponseEntity<>(blogPostList, HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(blogPostList, HttpStatus.FOUND);
        }
    }

}
