package com.example.alwaysSpring.service;

import com.example.alwaysSpring.domain.patients.Patients;
import com.example.alwaysSpring.domain.patients.PatientsRepository;
import com.example.alwaysSpring.dto.patients.PatientsRegisterRequestDto;
import com.example.alwaysSpring.dto.patients.PatientsRegisterResponseDto;
import com.example.alwaysSpring.dto.patients.PatientsResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientsRepository patientsRepository;

    public ResponseEntity<PatientsResponseDto> findAll() {
        List<Patients> patients = patientsRepository.findAll();
        PatientsResponseDto responseDto = new PatientsResponseDto(patients);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<PatientsRegisterResponseDto> register(PatientsRegisterRequestDto requestDto) {
        patientsRepository.save(requestDto.toEntity());
        PatientsRegisterResponseDto responseDto = new PatientsRegisterResponseDto(requestDto.toEntity());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
