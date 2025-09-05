package com.example.school_system.dtos;

import com.example.school_system.entities.Course;
import lombok.Data;

import java.util.List;

@Data
public class StudentCourseInfoDto {
    private String studentName;
    private List<String> courses;
}
