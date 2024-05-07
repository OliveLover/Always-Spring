package com.example.alwaysSpring.dto.patients;

import com.example.alwaysSpring.domain.patients.Patients;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PatientsResponseDto {
    List<Patients> patients;

    public PatientsResponseDto(List<Patients> patients) {
        this.patients = patients;
    }
}
