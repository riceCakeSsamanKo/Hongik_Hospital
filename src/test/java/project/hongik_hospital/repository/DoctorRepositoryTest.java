package project.hongik_hospital.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.hongik_hospital.domain.Department;
import project.hongik_hospital.domain.Doctor;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DoctorRepositoryTest {
    @Autowired
    DoctorRepository doctorRepository;

    @BeforeEach
    public void each() {
        Department department = new Department("정형외과", "123");
        Doctor doctor = new Doctor("닥터1", 10);
        department.addDoctor(doctor);
    }

    @Test
    public void find() {
        doctorRepository.findByName("닥터1");
    }
}