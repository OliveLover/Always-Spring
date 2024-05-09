package com.example.alwaysSpring.domain.patients;

import com.example.alwaysSpring.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Patients extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String dateOfBirth;

    @Column(nullable = false)
    private String sex;

    @Column
    private String phoneNum;

    @Column
    private String address;

    @Column
    private boolean activated = Boolean.TRUE;

    public void update(Patients entity) {
        this.name = entity.getName();
        this.dateOfBirth = entity.getDateOfBirth();
        this.sex = entity.getSex();
        this.phoneNum = entity.getPhoneNum();
        this.address = entity.getAddress();
    }

    public void sofDelete(Patients entity) {
        this.activated = Boolean.FALSE;
    }

    @Builder
    public Patients(String name, String sex, String dateOfBirth, String phoneNum, String address) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.phoneNum = phoneNum;
        this.address = address;
    }
}
