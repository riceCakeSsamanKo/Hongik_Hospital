package project.hongik_hospital.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import project.hongik_hospital.domain.Doctor;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class DoctorServiceTest {

    @Autowired
    DoctorService doctorService;

    @Test
    public void join() throws Exception{
        //given
        Doctor doc1 = new Doctor("닥터1", 10);
        Doctor doc2 = new Doctor("닥터2", 10);
        Doctor doc3 = new Doctor("닥터3", 20);

        //when
        doctorService.join(doc1);
        doctorService.join(doc2);
        doctorService.join(doc3);
        
        //then
        Assertions.assertThrows(IllegalStateException.class, () -> doctorService.join(doc1));
        Assertions.assertThrows(IllegalStateException.class, () -> doctorService.join(doc2));
        Assertions.assertThrows(IllegalStateException.class, () -> doctorService.join(doc3));
    }
}