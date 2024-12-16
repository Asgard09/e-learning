package com.example.AsgardShop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Entity
@Data
@Table(name = "submission")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "submission_question",
            joinColumns = @JoinColumn(name = "submission_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<Question> questions;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "selected_answer")
    private String selectedAnswer;

    @OneToOne(mappedBy = "submission", cascade = CascadeType.ALL)
    private Result result;
}
