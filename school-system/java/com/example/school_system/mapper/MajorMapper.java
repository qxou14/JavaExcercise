package com.example.school_system.mapper;

import com.example.school_system.dtos.MajorSummaryDto;
import com.example.school_system.entities.Major;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MajorMapper {

    @Mapping(target = "studentCount", expression = "java(major.getTotalStudent())")
    MajorSummaryDto toDto(Major major);
}
