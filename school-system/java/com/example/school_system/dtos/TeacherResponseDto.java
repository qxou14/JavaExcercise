package com.example.school_system.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeacherResponseDto {
    private String teacherName;
    private String email;
    private String studentName;
}
