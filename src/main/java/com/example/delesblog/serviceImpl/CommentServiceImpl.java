package com.example.delesblog.serviceImpl;

import com.example.delesblog.model.Comments;
import com.example.delesblog.model.Users;
import com.example.delesblog.repository.CommentRepository;
import com.example.delesblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class CommentServiceImpl {

    private final CommentRepository commentRepository;
    private UserRepository userRepository;


    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository){
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }


    public ResponseEntity<Comments> saveComment(Long userId, Comments newComment) {
        Optional<Users> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Users createdUsers = user.get();
            newComment.setUser(createdUsers);
            newComment.setContent(newComment.getContent());
            commentRepository.save(newComment);
            return new ResponseEntity<>(newComment, HttpStatus.CREATED);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Comments> editCommentById(Long commentId, Comments commentToEdit) {
        Optional<Comments> comment = commentRepository.findById(commentId);
        if (comment.isPresent()){
            Comments comment1 = comment.get();
            comment1.setContent(commentToEdit.getContent());
            commentRepository.save(comment1);
            return new ResponseEntity<>(comment1, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Comments>> findAllCommentById(Long postId) {
        List<Comments> findCommentById = commentRepository.findByUserId(postId);
        if (findCommentById.isEmpty()){
            return new ResponseEntity<>(findCommentById, HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(findCommentById, HttpStatus.FOUND);
        }

    }

    public ResponseEntity<List<Comments>> getAllComment() {
        List<Comments> allComment = commentRepository.findAll();
        return  new ResponseEntity<>(allComment, HttpStatus.FOUND);
    }

    public ResponseEntity<Void> deleteById(Long id) {
        Optional<Comments> comment = commentRepository.findById(id);
        if (comment.isPresent()){
            Comments comment1  = comment.get();
            commentRepository.deleteById(comment1.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Comments>> findCommentByContent(String content) {
        List<Comments> findByContent = commentRepository.findByContentIgnoringCaseContaining(content);
        if (findByContent.isEmpty()){
            return new ResponseEntity<>(findByContent, HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(findByContent, HttpStatus.FOUND);
        }
    }

    public ResponseEntity<Comments> likeCommentById(Long id) {
        Optional<Comments> commentToLike = commentRepository.findById(id);
        if (commentToLike.isPresent()){

            Comments comment = commentToLike.get();
            comment.setCommentLikes(comment.getCommentLikes() +1);
            commentRepository.save(comment);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Comments> unlikeCommentById(Long id) {
        Comments comment = commentRepository.findById(id).orElse(null);

        if (comment!= null && comment.getCommentLikes() > 0){
            comment.decrementComment();
            commentRepository.save(comment);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        }else {
            Comments zeroLikes = new Comments(comment.getContent(), comment.getCommentLikes());
            zeroLikes.setId(comment.getId());
            zeroLikes.setCommentDate(comment.getCommentDate());
            return new ResponseEntity<>(zeroLikes, HttpStatus.OK);
        }
    }
}

