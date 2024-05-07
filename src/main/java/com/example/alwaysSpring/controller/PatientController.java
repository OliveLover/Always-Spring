package com.example.alwaysSpring.controller;

import com.example.alwaysSpring.dto.patients.PatientsResponseDto;
import com.example.alwaysSpring.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping("/api/v1/patients")
    public ResponseEntity<PatientsResponseDto> findAll() {
        return patientService.findAll();
    }
}
