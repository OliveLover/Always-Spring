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

    @Column
    private String sex;

    @Column
    private String phoneNum;

    @Column
    private String address;

    @Builder
    public Patients (String name, String sex, String phoneNum, String address) {
        this.name = name;
        this.sex = sex;
        this.phoneNum = phoneNum;
        this.address = address;
    }

}
