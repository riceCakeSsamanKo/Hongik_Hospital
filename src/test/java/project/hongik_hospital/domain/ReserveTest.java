package project.hongik_hospital.domain;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static java.time.LocalDateTime.now;
import static project.hongik_hospital.domain.GenderType.MALE;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class ReserveTest {

    @Autowired
    EntityManager em;

    @Test
    public void createReserveTest() throws Exception {
        // 정상 케이스
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

        Reserve reserve = Reserve.createReserve(patient, doctor2, now());
        em.persist(reserve);

        // 오류 케이스
        Doctor noHosDoc = new Doctor("노병원닥터",100);
        try {
            Reserve.createReserve(patient, noHosDoc, now());
        } catch (IllegalStateException e) {
            System.out.println("e = " + e);
        }

    }
}