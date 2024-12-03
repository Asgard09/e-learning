package com.example.AsgardShop.service;

import com.example.AsgardShop.dto.request.UpdateDTO;
import com.example.AsgardShop.model.User;
import com.example.AsgardShop.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User updateUserByUsername(String email, UpdateDTO updateDTO) {
        // Tìm user theo username (hoặc email)
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Cập nhật thông tin user từ UpdateDTO
        user.setUsername(updateDTO.getUsername());
        user.setEmail(updateDTO.getEmail());
        user.setPassword(passwordEncoder.encode(updateDTO.getPassword()));
        user.setUpdatedAt(new Date());
        // Thêm các field cần thiết khác...

        // Lưu lại thay đổi
        return userRepository.save(user);
    }
}
