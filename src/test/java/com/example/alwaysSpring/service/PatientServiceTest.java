package com.example.alwaysSpring.service;

import com.example.alwaysSpring.domain.patients.Patients;
import com.example.alwaysSpring.domain.patients.PatientsRepository;
import com.example.alwaysSpring.dto.patients.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

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
                .dateOfBirth("1960-12-08")
                .sex("M")
                .phoneNum("000-0000-0000")
                .address("BUSAN")
                .build());

        patientsRepository.save(Patients.builder()
                .name("name2")
                .dateOfBirth("1980-07-21")
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
        assertThat(patientsList.get(0).getDateOfBirth()).isEqualTo("1960-12-08");
        assertThat(patientsList.get(0).getSex()).isEqualTo("M");
        assertThat(patientsList.get(0).getPhoneNum()).isEqualTo("000-0000-0000");
        assertThat(patientsList.get(0).getAddress()).isEqualTo("BUSAN");

        assertThat(patientsList.get(1).getName()).isEqualTo("name2");
        assertThat(patientsList.get(1).getDateOfBirth()).isEqualTo("1980-07-21");
        assertThat(patientsList.get(1).getSex()).isEqualTo("F");
        assertThat(patientsList.get(1).getPhoneNum()).isEqualTo("111-1111-1111");
        assertThat(patientsList.get(1).getAddress()).isEqualTo("SEOUL");
    }

    @Test
    void registerPatients_and_verifySavedData() throws Exception {
        //given
        String name = "name";
        String birthDay = "1960-12-08";
        String sex = "M";
        String phoneNum = "000-0000-0000";
        String address = "SEOUL";

        PatientsRegisterRequestDto requestDto = PatientsRegisterRequestDto.builder()
                .name(name)
                .dateOfBirth(birthDay)
                .sex(sex)
                .phoneNum(phoneNum)
                .address(address)
                .build();

        String url = "http://localhost:" + port + "/api/v1/patients";

        //when
        ResponseEntity<PatientsRegisterResponseDto> responseEntity = restTemplate
                .postForEntity(url, requestDto, PatientsRegisterResponseDto.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody())
                .extracting("name", "dateOfBirth", "sex", "phoneNum", "address")
                .containsExactly(name, birthDay, sex, phoneNum, address);

        List<Patients> all = patientsRepository.findAll();
        assertThat(all.get(0))
                .extracting("name", "dateOfBirth", "sex", "phoneNum", "address")
                .containsExactly(name, birthDay, sex, phoneNum, address);
    }

    @Test
    void givenValidPatientsId_whenUpdatePost_thenPatientsIsUpdatedCorrectly() throws Exception {
        //given
        Patients savePatients = patientsRepository.save(Patients.builder()
                .name("name")
                .dateOfBirth("1960-12-08")
                .sex("M")
                .phoneNum("000-0000-0000")
                .address("SEOUL")
                .build());

        Long updateId = savePatients.getId();
        String expectedName = "name2";
        String expectedDateOfBirth = "1977-03-05";
        String expectedSex = "F";
        String expectedPhoneNum = "111-1111-1111";
        String expectedAddress = "BUSAN";

        PatientsUpdateRequestDto requestDto = PatientsUpdateRequestDto.builder()
                .name(expectedName)
                .dateOfBirth(expectedDateOfBirth)
                .sex(expectedSex)
                .phoneNum(expectedPhoneNum)
                .address(expectedAddress)
                .build();

        String url = "http://localhost:" + port + "/api/v1/patients/" + updateId;

        HttpEntity<PatientsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<PatientsUpdateResponseDto> responseEntity = restTemplate
                .exchange(url, HttpMethod.PUT, requestEntity, PatientsUpdateResponseDto.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody())
                .extracting("name", "dateOfBirth", "sex", "phoneNum", "address")
                .containsExactly(expectedName, expectedDateOfBirth, expectedSex, expectedPhoneNum, expectedAddress);

        List<Patients> all = patientsRepository.findAll();
        assertThat(all.get(0))
                .extracting("name", "dateOfBirth", "sex", "phoneNum", "address")
                .containsExactly(expectedName, expectedDateOfBirth, expectedSex, expectedPhoneNum, expectedAddress);
    }

    @Test
    void softDeletePatients() throws Exception {
        //given
        Patients savePatients = patientsRepository.save(Patients.builder()
                .name("name")
                .dateOfBirth("1960-12-08")
                .sex("M")
                .phoneNum("000-0000-0000")
                .address("SEOUL")
                .build());

        Long softDeleteId = savePatients.getId();

        String url = "http://localhost:" + port + "/api/v1/patients/" + softDeleteId + "/delete";

        //when
        ResponseEntity<PatientsDeleteResponseDto> responseEntity = restTemplate
                .exchange(url, HttpMethod.PUT, null, PatientsDeleteResponseDto.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Optional<Patients> deletedPatients = patientsRepository.findById(softDeleteId);
        assertThat(deletedPatients).isPresent();
        assertThat(deletedPatients.get().isActivated()).isFalse();
    }
}