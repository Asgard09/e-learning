package com.example.AsgardShop.service;

import com.example.AsgardShop.base.Role;
import com.example.AsgardShop.base.StudyStatus;
import com.example.AsgardShop.model.Course;
import com.example.AsgardShop.model.Enrollment;
import com.example.AsgardShop.model.User;
import com.example.AsgardShop.repository.CourseRepository;
import com.example.AsgardShop.repository.EnrollmentRepository;
import com.example.AsgardShop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EnrollmentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public void enrollStudent(Long courseId, Long userId) {
        // Fetch user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // Fetch course
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId));

        // Check if user is already enrolled in the course
        boolean alreadyEnrolled = enrollmentRepository.existsByUserAndCourse(user, course);
        if (alreadyEnrolled) {
            throw new IllegalArgumentException("User is already enrolled in this course.");
        }

        // Create and save new enrollment
        Enrollment enrollment = new Enrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollment.setEnrollAt(new Date());
        enrollment.setProgress(0);
        enrollment.setStudyStatus(StudyStatus.STUDYING); // Adjust status as needed

        enrollmentRepository.save(enrollment);
    }
}
