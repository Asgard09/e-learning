package com.example.AsgardShop.repository;

import com.example.AsgardShop.model.Course;
import com.example.AsgardShop.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByCourse(Course course);
}
