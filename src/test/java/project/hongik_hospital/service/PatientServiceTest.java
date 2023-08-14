package project.hongik_hospital.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import project.hongik_hospital.domain.GenderType;
import project.hongik_hospital.domain.Patient;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class PatientServiceTest {
    @Autowired
    PatientService patientService;
    @Autowired
    EntityManager em;

    @BeforeEach
    public void each() {
        Patient patient1 = new Patient("patient", 10, GenderType.MALE);
        Patient patient2 = new Patient("patient", 20, GenderType.FEMALE);
        patient2.setLogIn("id","pw");

        patientService.join(patient1);
        patientService.join(patient2);
    }

    @Test
    public void remove() throws Exception{
        //given
        List<Patient> findPatients = patientService.findPatients("patient");
        Patient patient1 = findPatients.get(0);
        Patient patient2 = findPatients.get(1);

        //when
        boolean bool1 = patientService.removePatient(patient1);
        boolean bool2 = patientService.removePatient("id", "pw");
        boolean bool3 = patientService.removePatient(patient1);  // 또 제거한 경우 false

        //then
        assertEquals(bool1, true);
        assertEquals(bool2, true);
        assertEquals(bool3, false);
        assertEquals(patientService.findPatients("patient").size(),0);
    }
    @Test
    public void dirtyCheck() throws Exception{
        //given
        Patient patient = patientService.findPatient(1L);

        //when
        patient.setAge(12314124);
        //then

    }
}