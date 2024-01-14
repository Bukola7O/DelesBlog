package com.example.delesblog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_generator")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @Column(name = "commentDate")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date commentDate;

    private Long commentLikes;

    public Comments(String content, Long commentLikes) {
        this.content = content;
        this.commentLikes = commentLikes;
    }


    // Set likes to 0 when creating a new comment
    @PrePersist
    public void prePersist() {
        this.commentLikes = 0L;
    }

    public void incrementComment(){
        commentLikes++;
    }

    public void decrementComment() {
        commentLikes--;

    }
}

