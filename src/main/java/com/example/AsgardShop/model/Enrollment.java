package com.example.AsgardShop.model;

import com.example.AsgardShop.base.StudyStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Data
@Entity
@Table(name = "enrollment")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Long enrollmentId;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "enroll_at")
    private Date enrollAt;

    @Column(name = "progress")
    private double progress;// % Complete

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private StudyStatus studyStatus;

    @PrePersist
    @PreUpdate
    private void validateProgress() {
        if (this.progress > 100) {
            throw new IllegalArgumentException("Progress cannot exceed 100%");
        }
    }
}
