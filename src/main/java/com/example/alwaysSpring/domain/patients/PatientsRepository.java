package com.example.alwaysSpring.domain.patients;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientsRepository extends JpaRepository<Patients, Long> {
    List<Patients> findAllByActivatedTrue();
}
