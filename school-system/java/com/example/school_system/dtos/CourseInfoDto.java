package com.example.school_system.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CourseInfoDto {
    private String courseName;
    private List<String> students;
}
