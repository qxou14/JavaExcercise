package com.example.school_system.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String studentName;

    @Column(name = "email")
    private String email;

    //many students per major
    //one major many studentrs
    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;

    @OneToOne(mappedBy = "student")
    private Teacher teacher;

    @ManyToMany
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;


}
