package com.example.school_system.mapper;

import com.example.school_system.dtos.StudentRequestDto;
import com.example.school_system.dtos.StudentResponseDto;
import com.example.school_system.entities.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(source = "name", target = "studentName")
    @Mapping(source = "majorId", target = "major.id")
    Student toEntity(StudentRequestDto dto);

    StudentResponseDto toDto(Student student);
}
