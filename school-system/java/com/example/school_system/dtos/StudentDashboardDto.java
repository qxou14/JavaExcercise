package com.example.school_system.dtos;

import lombok.Data;

import java.util.List;

@Data
public class StudentDashboardDto {
    private String studentName;
    private String majorName;
    private List<CoursesWithGrades> coursesWithGrades;
}
