package project.hongik_hospital.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.hongik_hospital.domain.GenderType;
import project.hongik_hospital.domain.LogInInformation;
import project.hongik_hospital.domain.Patient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PatientRepositoryTest {

    @Autowired
    PatientRepository patientRepository;

    @BeforeEach //각 테스트마다 데이터 주입
    public void each() {
         Patient patient = new Patient("환자", 30, GenderType.MALE);
         patient.setLogIn("id","pw");
         patientRepository.save(patient);
    }

    @Test
    void findByLogInfo() {
        Patient byLogInfo = patientRepository.findByLogInfo("id", "pw");
        System.out.println("byLogInfo = " + byLogInfo.getName());

        Assertions.assertThat(byLogInfo.getName()).isEqualTo("환자");
    }
}