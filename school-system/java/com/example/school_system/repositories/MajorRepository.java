package com.example.school_system.repositories;

import com.example.school_system.entities.Major;
import com.example.school_system.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MajorRepository extends JpaRepository<Major,Integer> {
}
