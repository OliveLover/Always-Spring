package com.example.alwaysSpring.dto.patients;

import com.example.alwaysSpring.domain.patients.Patients;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PatientsUpdateRequestDto {
    private String name;
    private String dateOfBirth;
    private String sex;
    private String phoneNum;
    private String address;

    public Patients toEntity() {
        return Patients.builder()
                .name(name)
                .dateOfBirth(dateOfBirth)
                .sex(sex)
                .phoneNum(phoneNum)
                .address(address)
                .build();
    }
}
