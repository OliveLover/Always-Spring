package com.example.alwaysSpring.service;

import com.example.alwaysSpring.domain.patients.Patients;
import com.example.alwaysSpring.domain.patients.PatientsRepository;
import com.example.alwaysSpring.dto.patients.*;
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

    @Transactional
    public ResponseEntity<PatientsUpdateResponseDto> update(Long id, PatientsUpdateRequestDto requestDto) {
        Patients patients = patientsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("등록되지 않은 환자입니다.")
        );
        patients.update(requestDto.toEntity());
        PatientsUpdateResponseDto responseDto = new PatientsUpdateResponseDto(patients);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<PatientsDeleteResponseDto> softDelete(Long id) {
        Patients patients = patientsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("등록되지 않은 환자입니다.")
        );
        patients.sofDelete(patients);
        PatientsDeleteResponseDto responseDto = new PatientsDeleteResponseDto();
        responseDto.setMessage("삭제 처리 되었습니다.");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
