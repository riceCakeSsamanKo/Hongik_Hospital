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
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
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

    Address hospitalAddress1 = new Address("서울", "홍대거리", "12345");
    Address hospitalAddress2 = new Address("경기", "수내거리", "11111");

    Doctor doctor1 = new Doctor("박닥터", 10);
    Doctor doctor2 = new Doctor("최닥터", 8);

    Department department1 = new Department("정형외과", "02-1234-5678");
    Department department2 = new Department("성형외과", "02-1111-2222");

    TreatmentDate treatmentDate1 = new TreatmentDate(10, 9, 13, 20);
    TreatmentDate treatmentDate2 = new TreatmentDate(11, 3, 18, 10);


    @Test
    public void noDepartmentDocReserve() throws Exception {
        //given
        Hospital hospital = new Hospital("참조은병원", hospitalAddress1, department1);

        //then
        assertThrows(IllegalStateException.class, ()->Reserve.createReserve(patient1, doctor1, now(), treatmentDate1));
    }

    @Test
    public void noHospitalDocReserve() throws Exception {
        //when
        department1.addDoctor(doctor1);

        //then
        assertThrows(IllegalStateException.class, () ->Reserve.createReserve(patient1, doctor1, now(), treatmentDate1));
    }
    @Test
    public void reserveDocSameTime() throws Exception{
        //given
        Doctor doctor = new Doctor("이닥터", 30);
        department1.addDoctor(doctor);
        Hospital hospital = new Hospital("참조은병원", hospitalAddress1, department1);

        //when
        Reserve.createReserve(patient2, doctor, now(), treatmentDate1);

        //then (의사가 동일한 예약 시간을 가지게 된다면 오류 발생)
        assertThrows(IllegalStateException.class,()->Reserve.createReserve(patient1, doctor, now(), treatmentDate1));

    }

    @Test
    public void cancel() throws Exception{
        //given
        department1.addDoctor(doctor1);
        Hospital hospital = new Hospital("참조은병원", hospitalAddress1, department1);
        Reserve reserve = Reserve.createReserve(patient1, doctor1, now(), treatmentDate1);

        //when
        reserve.cancel();

        //then
        assertThat(reserve.getReserveStatus()).isEqualTo(ReserveStatus.CANCEL);
        assertThat(reserve.getDoctor().getTreatmentDates().size()).isEqualTo(0); //assertThat(doctor1.getTreatmentDates().size()).isEqualTo(0);
    }

    @Test
    public void complete() throws Exception{
        //given
        department1.addDoctor(doctor1);
        Hospital hospital = new Hospital("참조은병원", hospitalAddress1, department1);
        Reserve reserve = Reserve.createReserve(patient1, doctor1, now(), treatmentDate1);

        //when
        reserve.complete(10000);

        //then
        assertThat(reserve.getFee()).isEqualTo(10000);
        assertThat(reserve.getReserveStatus()).isEqualTo(ReserveStatus.COMPLETE);
    }
}