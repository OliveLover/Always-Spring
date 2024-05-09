package com.example.alwaysSpring.controller;

import com.example.alwaysSpring.dto.patients.*;
import com.example.alwaysSpring.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @CrossOrigin(originPatterns = "*")
    @GetMapping("/api/v1/patients")
    public ResponseEntity<PatientsResponseDto> findAll() {
        return patientService.findAll();
    }

    @CrossOrigin(originPatterns = "*")
    @PostMapping("/api/v1/patients")
    public ResponseEntity<PatientsRegisterResponseDto> register(@RequestBody PatientsRegisterRequestDto requestDto) {
        return patientService.register(requestDto);
    }

    @CrossOrigin(originPatterns = "*")
    @PutMapping("/api/v1/patients/{id}")
    public ResponseEntity<PatientsUpdateResponseDto> update(@PathVariable Long id, @RequestBody PatientsUpdateRequestDto requestDto) {
        return patientService.update(id, requestDto);
    }
}
