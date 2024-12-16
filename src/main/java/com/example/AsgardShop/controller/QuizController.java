package com.example.AsgardShop.controller;

import com.example.AsgardShop.dto.request.QuestionDTO;
import com.example.AsgardShop.dto.request.SubmissionDTO;
import com.example.AsgardShop.model.Question;
import com.example.AsgardShop.model.Result;
import com.example.AsgardShop.model.Submission;
import com.example.AsgardShop.repository.ResultRepository;
import com.example.AsgardShop.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    
    @Autowired
    private QuizService quizService;

    @Autowired
    private ResultRepository resultRepository;

    @PostMapping("/create_question")
    public ResponseEntity<Question> createQuestion(@RequestBody QuestionDTO questionDTO, @RequestParam Long courseId) {
        Question createdQuestion = quizService.createQuestion(questionDTO, courseId);
        return ResponseEntity.ok(createdQuestion);
    }

    // New endpoint for submitting quiz answers
    @PostMapping("/submit")
    public ResponseEntity<Submission> submitQuiz(@RequestBody SubmissionDTO submissionDTO, @RequestParam Long courseId) {
        Submission submission = quizService.submitQuiz(submissionDTO, courseId);
        return ResponseEntity.ok(submission);
    }

    @GetMapping("/result")
    public ResponseEntity<Result> getResult(@RequestParam Long id){
        Optional<Result> result = resultRepository.findById(id);
        return ResponseEntity.ok(result.get());
    }
}
