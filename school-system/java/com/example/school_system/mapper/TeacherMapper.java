package com.example.school_system.mapper;

import com.example.school_system.dtos.TeacherRequestDto;
import com.example.school_system.dtos.TeacherResponseDto;
import com.example.school_system.entities.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    TeacherRequestDto toDto(TeacherResponseDto response);

    Teacher toEntity(TeacherRequestDto request);
}
