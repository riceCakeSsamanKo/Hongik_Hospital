package project.hongik_hospital.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.hongik_hospital.domain.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.*;
import static org.junit.jupiter.api.Assertions.*;
import static project.hongik_hospital.domain.GenderType.*;
import static project.hongik_hospital.domain.ReserveStatus.*;

@SpringBootTest
@Transactional
class ReserveServiceTest {

    @Autowired
    ReserveService reserveService;

    @BeforeEach
    public void each() {
        User user = new User("환자1", 20, MALE);
        Department department = new Department("asdf", "123124");
        Hospital hospital = new Hospital("asfdf", new Address("123", "123", "123"), department);

        Doctor doctor = new Doctor("의사1", 10);
        department.addDoctor(doctor);

        TreatmentDate treatmentDate = new TreatmentDate(10,3,12,30);

        Reserve reserve = Reserve.createReserve(user, doctor, now(), treatmentDate);
        reserveService.saveReserve(reserve);
    }

    @Test
    void cancel() {
        //give
        List<Reserve> reserves = reserveService.findReserves("환자1");
        Reserve reserve = reserves.get(0);
        Doctor doctor = reserve.getDoctor();

        //when
        reserveService.cancel(reserve.getId());

        //then
        assertEquals(reserve.getReserveStatus(), CANCEL);
        assertEquals(doctor.getTreatmentDates().size(), 0);
    }
}