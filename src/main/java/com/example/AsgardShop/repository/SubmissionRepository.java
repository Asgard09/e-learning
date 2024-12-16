package com.example.AsgardShop.repository;

import com.example.AsgardShop.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}
