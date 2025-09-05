package com.example.school_system.services;

import com.example.school_system.dtos.TeacherRequestDto;
import com.example.school_system.dtos.TeacherResponseDto;
import com.example.school_system.entities.Student;
import com.example.school_system.entities.Teacher;
import com.example.school_system.mapper.TeacherMapper;
import com.example.school_system.repositories.StudentRepository;
import com.example.school_system.repositories.TeacherRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Setter
@Getter
@RequiredArgsConstructor
@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final TeacherMapper teacherMapper;

    public TeacherResponseDto assign(TeacherRequestDto request){
        //basically save to teacher's table
        //but teacher has a student object, so we need to take that into consideration
        Student student = studentRepository.findById(request.getStudentId()).orElse(null);
        Teacher teacher = teacherMapper.toEntity(request);
        teacher.setStudent(student);
        teacherRepository.save(teacher);
        return new TeacherResponseDto(request.getTeacherName(),request.getEmail(),student.getStudentName());

    }
}
