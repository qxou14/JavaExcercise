package com.example.school_system.services;

import com.example.school_system.dtos.MajorSummaryDto;
import com.example.school_system.entities.Major;
import com.example.school_system.mapper.MajorMapper;
import com.example.school_system.repositories.MajorRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@Service
public class MajorService {
    private final MajorRepository majorRepository;
    private final MajorMapper majorMapper;

    public List<MajorSummaryDto> getMajorSummary(){
        List<Major> majors = majorRepository.findAll();

        List<MajorSummaryDto> response = majors
                .stream()
                .map(major -> majorMapper.toDto(major))
                .toList();
        return response;
    }
}
