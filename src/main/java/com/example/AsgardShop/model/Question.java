package com.example.AsgardShop.model;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

@Entity
@Data
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_text")
    private String questionText;

    @Column(name = "option_A")
    private String optionA;

    @Column(name = "option_B")
    private String optionB;

    @Column(name = "option_C")
    private String optionC;

    @Column(name = "option_D")
    private String optionD;

    @Column(name = "correct_answer")
    private String correctAnswer;

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;

    @ManyToMany(mappedBy = "questions")
    private List<Submission> submissions;

    // Question ↔ Result (One-to-Many)
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Result> results;
}
