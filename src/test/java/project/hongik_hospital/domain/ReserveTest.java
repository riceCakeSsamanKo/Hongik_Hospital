package project.hongik_hospital.domain;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.*;
import static org.junit.jupiter.api.Assertions.*;
import static project.hongik_hospital.domain.GenderType.*;
import static project.hongik_hospital.domain.ReserveStatus.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class ReserveTest {

    @Autowired
    EntityManager em;

    @Test
    @Commit
    public void 예약() throws Exception {
        Patient patient = new Patient("김환자", 30, MALE);

        Address hospitalAddress = new Address("서울", "홍대거리", "12345");

        Doctor doctor1 = new Doctor("박닥터", 10);
        Doctor doctor2 = new Doctor("최닥터", 8);
        Doctor doctor3 = new Doctor("이닥터", 15);

        Department department = new Department("정형외과", "02-1234-5678");
        department.addDoctor(doctor1);
        department.addDoctor(doctor2);
        department.addDoctor(doctor3);

        Hospital hospital = new Hospital("참조은병원", hospitalAddress, department);

        Reserve reserve = new Reserve(patient, doctor1, department, hospital, now(), RESERVE);

        em.persist(reserve);
    }
}