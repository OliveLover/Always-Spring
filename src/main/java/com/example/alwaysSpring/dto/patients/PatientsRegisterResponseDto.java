package com.example.alwaysSpring.dto.patients;

import com.example.alwaysSpring.domain.patients.Patients;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PatientsRegisterResponseDto {
    private String name;
    private String dateOfBirth;
    private String sex;
    private String phoneNum;
    private String address;

    public PatientsRegisterResponseDto(Patients entity) {
        this.name = entity.getName();
        this.dateOfBirth = entity.getDateOfBirth();
        this.sex = entity.getSex();
        this.phoneNum = entity.getPhoneNum();
        this.address = entity.getAddress();
    }
}
