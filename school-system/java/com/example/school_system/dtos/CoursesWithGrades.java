package com.example.school_system.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoursesWithGrades {
    private String courseName;
    private String grade;
}
