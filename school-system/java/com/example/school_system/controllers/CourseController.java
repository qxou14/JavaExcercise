package com.example.school_system.controllers;

import com.example.school_system.dtos.*;
import com.example.school_system.entities.Student;
import com.example.school_system.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.school_system.services.StudentServices;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/enrollment")
    public ResponseEntity<CourseResponseDto> enrollStudentToCourse(@RequestBody CourseRequestDto request){
        CourseResponseDto response = courseService.enrollStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/{id}/courses")
    public ResponseEntity<StudentCourseInfoDto> getAllCoursesForStudents(@PathVariable(name = "id") Integer studentId){
        StudentCourseInfoDto response = courseService.getAllCoursesForStudents(studentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<CourseInfoDto> getAllStudentsInCourse(@PathVariable(name = "id") Integer courseId){
        CourseInfoDto response = courseService.getAllStudentsInCourse(courseId);
        return ResponseEntity.ok(response);
    }

}
