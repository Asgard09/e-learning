package com.example.AsgardShop.service;

import com.example.AsgardShop.dto.request.QuestionDTO;
import com.example.AsgardShop.dto.request.SubmissionDTO;
import com.example.AsgardShop.model.Course;
import com.example.AsgardShop.model.Question;
import com.example.AsgardShop.model.Submission;
import com.example.AsgardShop.model.Result;
import com.example.AsgardShop.repository.CourseRepository;
import com.example.AsgardShop.repository.QuestionRepository;
import com.example.AsgardShop.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {
    
    @Autowired
    private QuestionRepository questionRepository;
    
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    public Question createQuestion(QuestionDTO questionDTO, Long courseId) {
        // Create a new Question entity
        Question question = new Question();
        
        // Set question details from DTO
        question.setQuestionText(questionDTO.getQuestionText());
        question.setOptionA(questionDTO.getOptionA());
        question.setOptionB(questionDTO.getOptionB());
        question.setOptionC(questionDTO.getOptionC());
        question.setOptionD(questionDTO.getOptionD());
        question.setCorrectAnswer(questionDTO.getCorrectAnswer());
        
        // Find and set the associated course
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found"));
        question.setCourse(course);
        
        // Save and return the question
        return questionRepository.save(question);
    }

    @Transactional
    public Submission submitQuiz(SubmissionDTO submissionDTO, Long courseId) {
        // Find the course
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found"));

        // Find all questions for this course
        List<Question> courseQuestions = questionRepository.findByCourse(course);

        // Create submission
        Submission submission = new Submission();
        submission.setStudentName(submissionDTO.getStudentName());
        submission.setQuestions(courseQuestions);

        // Calculate score
        int correctAnswers = 0;
        for (Question question : courseQuestions) {
            String selectedAnswer = submissionDTO.getSelectedAnswers().get(question.getId());
            if (selectedAnswer != null && selectedAnswer.equals(question.getCorrectAnswer())) {
                correctAnswers++;
            }
            submission.setSelectedAnswer(selectedAnswer);
        }

        // Create and set result
        Result result = new Result();
        result.setSubmission(submission);
        result.setScore((double) correctAnswers / courseQuestions.size() * 100);
        result.setStudentName(submissionDTO.getStudentName());
        result.setCourse(course.getCourseName());
        result.setQuestion(questionRepository.findByCourse(course).get(0));
        submission.setResult(result);

        // Save submission
        return submissionRepository.save(submission);
    }
}
