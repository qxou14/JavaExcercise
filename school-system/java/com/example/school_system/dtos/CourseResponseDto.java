package com.example.school_system.dtos;

import lombok.Data;

@Data
public class CourseResponseDto {

    private Integer studentId;
    private Integer courseId;
    private String status;
}
