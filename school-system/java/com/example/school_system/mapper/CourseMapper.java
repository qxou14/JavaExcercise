package com.example.school_system.mapper;

import com.example.school_system.dtos.CourseRequestDto;
import com.example.school_system.dtos.CourseResponseDto;
import com.example.school_system.dtos.StudentCourseInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    void convertRequestToResponse(CourseRequestDto request, @MappingTarget CourseResponseDto response);
    CourseResponseDto toResponse(CourseRequestDto request);
}
