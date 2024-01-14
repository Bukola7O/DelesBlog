package com.example.delesblog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "title")
    private String title;

//   @Column(name = "desc")
    private  String description;

//  @Column(columnDefinition = "Date")
    @CurrentTimestamp
    private LocalDate userDate;

    @CurrentTimestamp
    private LocalTime userTime;

    public BlogPost(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
