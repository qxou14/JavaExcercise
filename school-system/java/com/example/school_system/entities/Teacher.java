package com.example.school_system.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "teacher")
@Data
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String teacherName;

    @Column(name = "email")
    private String email;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
