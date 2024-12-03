package com.example.AsgardShop.repository;

import com.example.AsgardShop.model.Course;
import com.example.AsgardShop.model.Enrollment;
import com.example.AsgardShop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByUserAndCourse(User user, Course course);
}
