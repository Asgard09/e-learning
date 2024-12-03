package com.example.AsgardShop.service;

import com.example.AsgardShop.base.Role;
import com.example.AsgardShop.model.Course;
import com.example.AsgardShop.model.User;
import com.example.AsgardShop.repository.CourseRepository;
import com.example.AsgardShop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    public Course createCourse(Course course) {
        User instructor = userRepository.findById(course.getInstructor().getId())
                .orElseThrow(() -> new RuntimeException("Instructor not found"));
        if (!instructor.getRole().equals(Role.TEACHER)) {
            throw new RuntimeException("The assigned instructor must have the TEACHER role");
        }
        course.setCreateAt(new Date());
        course.setUpdateAt(new Date());

        return courseRepository.save(course);
    }

    // Method to get all course names
    public List<String> getAllCourseNames() {
        List<Course> courses = courseRepository.findAll();
        List<String> courseNames = new ArrayList<>();

        for (Course course : courses) {
            courseNames.add(course.getCourseName());
        }

        return courseNames;
    }

    // Method to get the course details by ID
    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId).orElse(null); // Return course if found, or null
    }

    public void deleteCourseById(Long courseId) {
        // Check if the course exists before deleting
        if (courseRepository.existsById(courseId)) {
            courseRepository.deleteById(courseId);
        } else {
            throw new RuntimeException("Course not found with ID: " + courseId);
        }
    }
}
