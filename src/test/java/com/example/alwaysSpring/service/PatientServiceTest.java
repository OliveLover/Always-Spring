package com.example.alwaysSpring.service;

import com.example.alwaysSpring.domain.patients.Patients;
import com.example.alwaysSpring.domain.patients.PatientsRepository;
import com.example.alwaysSpring.dto.patients.PatientsResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PatientServiceTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PatientsRepository patientsRepository;

    @AfterEach
    public void tearDown() {
        patientsRepository.deleteAll();
    }

    @Test
    void findId_and_verify() throws Exception {
        //given
        patientsRepository.save(Patients.builder()
                .name("name1")
                .sex("M")
                .phoneNum("000-0000-0000")
                .address("BUSAN")
                .build());

        patientsRepository.save(Patients.builder()
                .name("name2")
                .sex("F")
                .phoneNum("111-1111-1111")
                .address("SEOUL")
                .build());

        String url = "http://localhost:" + port + "/api/v1/patients";

        //when
        ResponseEntity<PatientsResponseDto> responseEntity = restTemplate
                .exchange(url, HttpMethod.GET, null, PatientsResponseDto.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Patients> patientsList = responseEntity.getBody().getPatients();

        assertThat(patientsList.get(0).getName()).isEqualTo("name1");
        assertThat(patientsList.get(0).getSex()).isEqualTo("M");
        assertThat(patientsList.get(0).getPhoneNum()).isEqualTo("000-0000-0000");
        assertThat(patientsList.get(0).getAddress()).isEqualTo("BUSAN");

        assertThat(patientsList.get(1).getName()).isEqualTo("name2");
        assertThat(patientsList.get(1).getSex()).isEqualTo("F");
        assertThat(patientsList.get(1).getPhoneNum()).isEqualTo("111-1111-1111");
        assertThat(patientsList.get(1).getAddress()).isEqualTo("SEOUL");
    }
}