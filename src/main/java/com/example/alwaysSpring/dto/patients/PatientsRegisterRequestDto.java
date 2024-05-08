package com.example.alwaysSpring.dto.patients;

import com.example.alwaysSpring.domain.patients.Patients;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PatientsRegisterRequestDto {
    private String name;
    private String birthDay;
    private String sex;
    private String phoneNum;
    private String address;

    public Patients toEntity() {
        return Patients.builder()
                .name(name)
                .birthDay(birthDay)
                .sex(sex)
                .phoneNum(phoneNum)
                .address(address)
                .build();
    }
}
