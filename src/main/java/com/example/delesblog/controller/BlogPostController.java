package com.example.delesblog.controller;

import com.example.delesblog.model.BlogPost;
import com.example.delesblog.serviceImpl.BlogPostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BlogPostController {

    private BlogPostServiceImpl blogPostService;

    @Autowired
    public BlogPostController(BlogPostServiceImpl blogPostService) {
        this.blogPostService = blogPostService;
    }

    @PostMapping("/save-blogPost")
    public ResponseEntity<BlogPost> saveBlogPost(@RequestBody BlogPost blogPost){
        return blogPostService.saveBlogPost(blogPost);
    }
    @GetMapping("/all-blogPosts")
    public ResponseEntity<List<BlogPost>> getAllBlogPost(){
        return blogPostService.getAllBlogPost();
    }
    @PutMapping("/edit-blogPost/{id}")
    public ResponseEntity<BlogPost> editBlogPostById(@PathVariable Long id, @RequestBody BlogPost newBlogPost) {
       return blogPostService.editBlogPostById(id, newBlogPost);
    }
    @GetMapping ("/get-blogPost/{id}")
        public ResponseEntity<BlogPost> getBlogPostById (@PathVariable Long id) {
        return blogPostService.getBlogPostById(id);
        }
        @DeleteMapping("/delete-blogPost/{id}")
    public ResponseEntity<Void> deleteBlogPostById(@PathVariable Long id) {
        return blogPostService.deleteBlogPostById(id);
        }
        @GetMapping("/get-blogPost-title/{title}")
    public ResponseEntity<List<BlogPost>> getBlogPostByBlogPostTitle(@PathVariable String title){
        return blogPostService.getBlogPostByBlogPostTitle(title);
        }
}
