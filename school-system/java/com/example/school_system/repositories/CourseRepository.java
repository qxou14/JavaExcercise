package com.example.school_system.repositories;

import com.example.school_system.dtos.CourseRequestDto;
import com.example.school_system.dtos.CourseResponseDto;
import com.example.school_system.entities.Course;
import org.mapstruct.MappingTarget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Integer> {


}
