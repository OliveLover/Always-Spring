package com.example.alwaysSpring.dto.patients;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PatientsDeleteResponseDto {
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }
}
