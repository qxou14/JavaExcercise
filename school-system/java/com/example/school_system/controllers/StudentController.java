package com.example.school_system.controllers;

import com.example.school_system.dtos.StudentDashboardDto;
import com.example.school_system.dtos.StudentRequestDto;
import com.example.school_system.dtos.StudentResponseDto;
import com.example.school_system.dtos.TeacherResponseDto;
import com.example.school_system.entities.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.school_system.services.StudentServices;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentServices studentServices;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents(){
        List<Student> student = studentServices.getAllStudents();
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody StudentRequestDto request){
        Student student = studentServices.createStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable Integer id){
        StudentResponseDto dto = studentServices.getStudentById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}/dashboard")
    public ResponseEntity<StudentDashboardDto> getStudentDashboard(@PathVariable Integer id){
        StudentDashboardDto dto = studentServices.getStudentDashboard(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}/teacher")
    public ResponseEntity<TeacherResponseDto> findTeacherByStudentId(@PathVariable(name = "id") Integer studentId){
        TeacherResponseDto dto = studentServices.findTeacherByStudentId(studentId);
        return ResponseEntity.ok(dto);
    }

}
