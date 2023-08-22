package project.hongik_hospital.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import project.hongik_hospital.domain.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;
import static project.hongik_hospital.domain.GenderType.FEMALE;
import static project.hongik_hospital.domain.GenderType.MALE;
import static project.hongik_hospital.domain.ReserveStatus.RESERVE;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class ReserveRepositoryTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private ReserveRepository reserveRepository;

    @BeforeEach //각 테스트마다 데이터 주입
    public void each() {
        User user1 = new User("김환자", 30, MALE);
        User user2 = new User("양환자", 28, FEMALE);

        Address hospitalAddress1 = new Address("서울", "홍대거리", "12345");
        Address hospitalAddress2 = new Address("성남", "분당거리", "99999");

        Doctor doctor1 = new Doctor("박닥터", 10);
        Doctor doctor2 = new Doctor("최닥터", 8);
        Doctor doctor3 = new Doctor("이닥터", 15);
        Doctor doctor4 = new Doctor("전닥터", 9);
        Doctor doctor5 = new Doctor("신닥터", 17);

        Department department1 = new Department("정형외과", "02-1234-5678");
        department1.addDoctor(doctor1);
        department1.addDoctor(doctor2);

        Department department2 = new Department("외과","02-1523-4532");
        department2.addDoctor(doctor3);

        Department department3 = new Department("성형외과", "031-1111-2222");
        department3.addDoctor(doctor4);
        department3.addDoctor(doctor5);

        Hospital hospital1 = new Hospital("참조은병원", hospitalAddress1, department1, department2);
        Hospital hospital2 = new Hospital("짱조은병원", hospitalAddress2, department3);

        TreatmentDate treatmentDate1 = new TreatmentDate(12,1,14,12);
        TreatmentDate treatmentDate2 = new TreatmentDate(1,23,14,10);
        TreatmentDate treatmentDate3 = new TreatmentDate(6,7,10,30);

        Reserve reserve1 = Reserve.createReserve(user2, doctor1, now(),treatmentDate1);
        Reserve reserve2 = Reserve.createReserve(user1, doctor3, now(),treatmentDate2);
        Reserve reserve3 = Reserve.createReserve(user2, doctor4, now(),treatmentDate3);

        reserveRepository.save(reserve1);
        reserveRepository.save(reserve2);
        reserveRepository.save(reserve3);
    }
    @Test
    void findAll() {
        List<Reserve> results = reserveRepository.findAll();
        for (Reserve result : results) {
            System.out.println("result = " + result);
        }
        Assertions.assertThat(results.size()).isEqualTo(3);
    }

    @Test
    void findByUserName() {
        List<Reserve> findReserve = reserveRepository.findByUserName("김환자");

        for (Reserve reserve : findReserve) {
            System.out.println("reserve = " + reserve);
        }
        Assertions.assertThat(findReserve.get(0).getDoctor().getName()).isEqualTo("이닥터");
        Assertions.assertThat(findReserve.get(0).getUser().getName()).isEqualTo("김환자");
    }

    @Test
    void findByStatus() {
        List<Reserve> reserved = reserveRepository.findByStatus(RESERVE);
        List<Reserve> canceled = reserveRepository.findByStatus(ReserveStatus.CANCEL);
        List<Reserve> completed = reserveRepository.findByStatus(ReserveStatus.COMPLETE);

        for (Reserve reserve : completed) {
            System.out.println("reserve = " + reserve);
        }
        Assertions.assertThat(reserved.size()).isEqualTo(3);
        Assertions.assertThat(canceled.size()).isEqualTo(0);
        Assertions.assertThat(completed.size()).isEqualTo(0);
    }
}