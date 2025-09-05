package com.example.school_system.controllers;

import com.example.school_system.dtos.CourseRequestDto;
import com.example.school_system.dtos.CourseResponseDto;
import com.example.school_system.dtos.MajorSummaryDto;
import com.example.school_system.services.MajorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/majors")
@RequiredArgsConstructor
public class MajorController {

    private final MajorService majorService;

    @GetMapping("/summary")
    public ResponseEntity<List<MajorSummaryDto>> getMajorSummary(){
        List<MajorSummaryDto> response = majorService.getMajorSummary();
        return ResponseEntity.ok(response);
    }
}
