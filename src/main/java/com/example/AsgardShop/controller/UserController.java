package com.example.AsgardShop.controller;

import com.example.AsgardShop.dto.request.UpdateDTO;
import com.example.AsgardShop.model.User;
import com.example.AsgardShop.repository.UserRepository;
import com.example.AsgardShop.service.UserService;
import com.example.AsgardShop.service.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody UpdateDTO updateDTO, HttpServletRequest request) {
        try {
            // Lấy token từ header Authorization
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            // Tách token ra khỏi prefix "Bearer "
            String token = authHeader.substring(7);

            // Giải mã token và lấy thông tin userId (hoặc email/username)
            String username = jwtService.extractUserEmail(token);

            // Gọi service để cập nhật thông tin user
            User updatedUser = userService.updateUserByUsername(username, updateDTO);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
