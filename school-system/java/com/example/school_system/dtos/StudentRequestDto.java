package com.example.school_system.dtos;

import lombok.Data;

@Data
public class StudentRequestDto {

    private String name;
    private String email;
    private Integer majorId;
}
