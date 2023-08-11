package project.hongik_hospital.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static java.time.LocalDateTime.now;
import static project.hongik_hospital.domain.GenderType.FEMALE;
import static project.hongik_hospital.domain.GenderType.MALE;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class ReserveTest {

    @Autowired
    EntityManager em;

    Patient patient1 = new Patient("김환자", 30, MALE);
    Patient patient2 = new Patient("박환자", 35, MALE);
    Patient patient3 = new Patient("구환자", 20, FEMALE);

    Address hospitalAddress1 = new Address("서울", "홍대거리", "12345");
    Address hospitalAddress2 = new Address("경기", "수내거리", "11111");

    Doctor doctor1 = new Doctor("박닥터", 10);
    Doctor doctor2 = new Doctor("최닥터", 8);
    Doctor doctor3 = new Doctor("이닥터", 15);

    Department department1 = new Department("정형외과", "02-1234-5678");
    Department department2 = new Department("성형외과", "02-1111-2222");

    TreatmentDate treatmentDate1 = new TreatmentDate(10, 9, 13, 20);
    TreatmentDate treatmentDate2 = new TreatmentDate(11, 3, 18, 10);


    @Test
    public void createReserveTest() throws Exception {
        // 정상 케이스
        department1.addDoctor(doctor1);
        department1.addDoctor(doctor2);
        department1.addDoctor(doctor3);

        Hospital hospital = new Hospital("참조은병원", hospitalAddress1, department1);
        Reserve reserve = Reserve.createReserve(patient1, doctor2, now(), treatmentDate1);
        em.persist(reserve);

        // 오류 케이스
        Doctor noHosDoc = new Doctor("노병원닥터",100);
        Assertions.assertThrows(IllegalStateException.class, () -> Reserve.createReserve(patient1, noHosDoc, now(), treatmentDate2));
    }

    @Test
    public void createReserveTest2() throws Exception {

        //given
        Doctor doctor = new Doctor("이닥터", 30);

        //when
        doctor.addTreatmentDate(treatmentDate1);
        doctor.addTreatmentDate(treatmentDate2);

        //then
        Assertions.assertThrows(IllegalStateException.class,()->Reserve.createReserve(patient1, doctor, now(), treatmentDate1));
    }
}