package com.example.school_system.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name ="name")
    private String courseName;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students;
}
