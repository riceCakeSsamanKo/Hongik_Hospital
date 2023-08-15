package project.hongik_hospital.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;
import project.hongik_hospital.domain.GenderType;
import project.hongik_hospital.domain.Patient;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class PatientRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    PatientRepository patientRepository;

    @BeforeEach //각 테스트마다 데이터 주입
    public void each() {
        Patient patient = new Patient("환자", 30, GenderType.MALE);
        patient.setLogIn("id", "pw");
        patientRepository.save(patient);
    }

    @Test
    void find() {
        List<Patient> patient = patientRepository.findByName("환자1");
    }

    @Test
    void findByLogInfo() {
        Optional<Patient> byLogInfoOptional = patientRepository.findByLogInfo("id", "pw");
        Patient byLogInfo = byLogInfoOptional.get();
        System.out.println("byLogInfo = " + byLogInfo.getName());

        assertThat(byLogInfo.getName()).isEqualTo("환자");
    }

    @Test
    public void remove() throws Exception {
        //given
        List<Patient> findPatient = patientRepository.findByName("환자");
        Patient patient = findPatient.get(0);

        //when
        patientRepository.remove(patient);

        //then
        assertThat(patientRepository.findByName("환자").size()).isEqualTo(0);
        assertThrows(InvalidDataAccessApiUsageException.class, () -> patientRepository.remove(patient));
    }
}