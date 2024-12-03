package com.example.AsgardShop.controller;

import com.example.AsgardShop.model.Course;
import com.example.AsgardShop.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/create")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        try {
            Course createdCourse = courseService.createCourse(course);
            return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint to get a list of course names
    @GetMapping("/names")
    public ResponseEntity<List<String>> getAllCourseNames() {
        List<String> courseNames = courseService.getAllCourseNames();
        return new ResponseEntity<>(courseNames, HttpStatus.OK);
    }

    @GetMapping("/view_details")
    public ResponseEntity<Course> getCourseById(@RequestParam Long courseId) {
        Course course = courseService.getCourseById(courseId);

        if (course == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 if course not found
        }

        return new ResponseEntity<>(course, HttpStatus.OK); // Return course details with 200 OK
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCourse(@RequestParam Long courseId) {
        try {
            courseService.deleteCourseById(courseId);
            return new ResponseEntity<>("Course deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
