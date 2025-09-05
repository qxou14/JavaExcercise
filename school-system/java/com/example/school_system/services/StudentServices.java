package com.example.school_system.services;

import com.example.school_system.dtos.*;
import com.example.school_system.entities.Course;
import com.example.school_system.entities.Major;
import com.example.school_system.entities.Student;
import com.example.school_system.entities.Teacher;
import com.example.school_system.mapper.StudentMapper;
import com.example.school_system.repositories.MajorRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import com.example.school_system.repositories.StudentRepository;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@Service
public class StudentServices {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final MajorRepository majorRepository;

    public List<Student> getAllStudents(){
        return  studentRepository.findAll();
    }

    public Student createStudent(StudentRequestDto request){
        Student student = studentMapper.toEntity(request);
        Major major = majorRepository.findById(request.getMajorId()).orElseThrow(() -> new RuntimeException("Major not found"));
        student.setMajor(major);
        studentRepository.save(student);
        student.setId(student.getId());
        return student;
    }

    public StudentResponseDto getStudentById(Integer id){
        Student student = studentRepository.findById(id).orElse(null);
        StudentResponseDto dto = studentMapper.toDto(student);
        dto.setMajorName(student.getMajor().getMajorName());
        return dto;
    }

    public StudentDashboardDto getStudentDashboard(Integer studentId){
        Student student = studentRepository.findById(studentId).orElse(null);
        List<CoursesWithGrades> coursesWithGrades = getCoursesWithGrades(student.getCourses());
        StudentDashboardDto response = new StudentDashboardDto();
        response.setStudentName(student.getStudentName());
        response.setMajorName(student.getMajor().getMajorName());
        response.setCoursesWithGrades(coursesWithGrades);
        return response;
    }

    private List<CoursesWithGrades> getCoursesWithGrades(List<Course> courses){
        return courses.stream().map(course -> new CoursesWithGrades(course.getCourseName(),"A")).toList();
    }

    public TeacherResponseDto findTeacherByStudentId(Integer studentId){
        Student student = studentRepository.findById(studentId).orElse(null);
        Teacher studentTeacher = student.getTeacher();
        TeacherResponseDto response = new TeacherResponseDto(studentTeacher.getTeacherName(),studentTeacher.getEmail(),student.getStudentName());
        return response;
    }

}
