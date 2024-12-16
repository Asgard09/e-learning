package com.example.AsgardShop.controller;

import com.example.AsgardShop.model.Course;
import com.example.AsgardShop.model.User;
import com.example.AsgardShop.repository.UserRepository;
import com.example.AsgardShop.service.EnrollmentService;
import com.example.AsgardShop.service.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/enroll")
    public ResponseEntity<String> enrollStudent(@RequestParam Long courseId, HttpServletRequest request) {
        try{
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            // Tách token ra khỏi prefix "Bearer "
            String token = authHeader.substring(7);

            // Giải mã token và lấy thông tin userId (hoặc email/username)
            String email = jwtService.extractUserEmail(token);

            Optional<User> user = userRepository.findByEmail(email);

            if (user.isEmpty()){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated.");
            }
            enrollmentService.enrollStudent(courseId,user.get().getId());
            return ResponseEntity.ok("Student enrolled successfully.");
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during enrollment.");
        }
    }

    @GetMapping("/completed-courses")
    public ResponseEntity<List<Course>> getCompletedCourses(/*HttpServletRequest request*/ @RequestParam Long studentId) {
        /* Use to extract information from request
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);
        String email = jwtService.extractUserEmail(token);
        Optional<User> user = userRepository.findByEmail(email);
         */
        Optional<User> user = userRepository.findById(studentId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        List<Course> completedCourses = enrollmentService.getCompletedCourses(user.get().getId());
        return ResponseEntity.ok(completedCourses);
    }

    @GetMapping("/students-in-course")
    public ResponseEntity<List<User>> getStudentsInCourse(@RequestParam Long courseId) {
        List<User> students = enrollmentService.getStudentsInCourse(courseId);
        return ResponseEntity.ok(students);
    }
}
