package com.example.AsgardShop.repository;

import com.example.AsgardShop.model.Course;
import com.example.AsgardShop.model.Enrollment;
import com.example.AsgardShop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByUserAndCourse(User user, Course course);
    List<Enrollment> findByUserAndProgress(User user, double progress);
    List<Enrollment> findByCourse(Course course);
}
