package com.example.delesblog.controller;

import com.example.delesblog.model.Comments;
import com.example.delesblog.serviceImpl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentsController {
    private CommentServiceImpl commentService;
    @Autowired
    public CommentsController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/create-comment/{postId}")
    public ResponseEntity<Comments> createComment(@PathVariable Long postId, @RequestBody Comments newComment){
        return commentService.saveComment(postId, newComment);

    }

    @PutMapping("/edit-comment/{commentId}")
    public ResponseEntity<Comments> editCommentById(@PathVariable Long commentId, @RequestBody Comments commentToEdit){
        return commentService.editCommentById(commentId,commentToEdit);
    }

    @GetMapping("/find-comment-postId/{postId}")
    public ResponseEntity<List<Comments>>  findAllCommentByUserId(@PathVariable Long postId){
        return commentService.findAllCommentById(postId);
    }

    @GetMapping("/all-comment")
    public ResponseEntity<List<Comments>> getAllComment() {
        return commentService.getAllComment();
    }

    @GetMapping("/get-comment/{content}")
    public ResponseEntity<List<Comments>> searchCommentByContent(@PathVariable String content){
        return commentService.findCommentByContent(content);
    }


    @DeleteMapping("delete-comment/{id}")
    public  ResponseEntity<Void> deleteCommentById(@PathVariable Long id){
        return commentService.deleteById(id);
    }

    @PutMapping("/like-comment/{id}")
    public ResponseEntity<Comments> likeCommentById(@PathVariable Long id){
        return commentService.likeCommentById(id);
    }

    @PutMapping("/unlike-comment/{id}")
    public ResponseEntity<Comments> unlikeCommentById(@PathVariable Long id){
        return commentService.unlikeCommentById(id);
    }


}
