package com.example.school_system.dtos;

import lombok.Data;

@Data
public class TeacherRequestDto {
    private String teacherName;
    private String email;
    private Integer studentId;
}
