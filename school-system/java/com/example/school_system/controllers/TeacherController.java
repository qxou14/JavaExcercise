package com.example.school_system.controllers;

import com.example.school_system.dtos.TeacherRequestDto;
import com.example.school_system.dtos.TeacherResponseDto;
import com.example.school_system.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping
    public ResponseEntity<TeacherResponseDto> assign(@RequestBody TeacherRequestDto request){
        TeacherResponseDto response = teacherService.assign(request);
        return ResponseEntity.ok(response);
    }

}
