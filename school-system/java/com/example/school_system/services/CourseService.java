package com.example.school_system.services;

import com.example.school_system.dtos.CourseInfoDto;
import com.example.school_system.dtos.CourseRequestDto;
import com.example.school_system.dtos.CourseResponseDto;
import com.example.school_system.dtos.StudentCourseInfoDto;
import com.example.school_system.entities.Course;
import com.example.school_system.entities.Student;
import com.example.school_system.mapper.CourseMapper;
import com.example.school_system.mapper.StudentMapper;
import com.example.school_system.repositories.CourseRepository;
import com.example.school_system.repositories.MajorRepository;
import com.example.school_system.repositories.StudentRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@Service
public class CourseService {
    private final StudentRepository studentRepository;
    private final CourseMapper courseMapper;
    private final CourseRepository courseRepository;


    public CourseResponseDto enrollStudent(CourseRequestDto request){
        //student table is owning the many to many join table, so we need to make changes to it
        //JPA will make change auto to the join table

        //happy path only. No exception handling
        Student student = studentRepository.findById(request.getStudentId()).orElse(null);
        Course course = courseRepository.findById(request.getCourseId()).orElse(null);
        student.getCourses().add(course);
        studentRepository.save(student);
        CourseResponseDto response = courseMapper.toResponse(request);
        response.setStatus("Enrolled");
        return response;
    }

    public StudentCourseInfoDto getAllCoursesForStudents(Integer studentId){

        //we have to get the student
        //fetch student course
        //this likely need to deal with many to many relationship as join table has the info

        Student student = studentRepository.findById(studentId).orElse(null);
        List<String> courses = ConvertCourseToCourseName(student.getCourses());
        StudentCourseInfoDto responseDto = new StudentCourseInfoDto();
        responseDto.setStudentName(student.getStudentName());
        responseDto.setCourses(courses);
        return responseDto;
    }

    public CourseInfoDto  getAllStudentsInCourse(Integer courseId){
        Course course = courseRepository.findById(courseId).orElse(null);
        List<String> students = ConvertStudentToStudentName(course.getStudents());
        CourseInfoDto responseDto = new CourseInfoDto();
        responseDto.setStudents(students);
        responseDto.setCourseName(course.getCourseName());
        return responseDto;
    }

    private List<String> ConvertCourseToCourseName(List<Course> courses){
        return courses
                .stream()
                .map(course -> course.getCourseName())
                .toList();
    }

    private List<String> ConvertStudentToStudentName(List<Student> students){
        return students
                .stream()
                .map(student -> student.getStudentName())
                .toList();
    }
}
