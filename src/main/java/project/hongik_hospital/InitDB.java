package project.hongik_hospital;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.hongik_hospital.domain.*;
import project.hongik_hospital.repository.HospitalRepository;
import project.hongik_hospital.service.DoctorService;
import project.hongik_hospital.service.HospitalService;
import project.hongik_hospital.service.UserService;

import javax.annotation.PostConstruct;

import static project.hongik_hospital.domain.AccountType.*;
import static project.hongik_hospital.domain.GenderType.*;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct  // 빈으로 등록될 시 자동으로 실행됨
    public void init(){
        initService.hospitalInit();
        initService.userInit();
        initService.adminInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final UserService userService;
        private final DoctorService doctorService;
        private final HospitalService hospitalService;
        // 필요시 더 추가할 것

        void hospitalInit() {
            Doctor doctor1 = new Doctor("정형외과 닥터1", 10);
            Doctor doctor2 = new Doctor("정형외과 닥터2", 10);
            Doctor doctor3 = new Doctor("성형외과 닥터1", 10);
            Doctor doctor4 = new Doctor("성형외과 닥터2", 10);
            Doctor doctor5 = new Doctor("외과 닥터1", 10);

            Department dp1 = new Department("정형외과", "031-1111-1111");
            dp1.addDoctor(doctor1);
            dp1.addDoctor(doctor2);

            Department dp2 = new Department("성형외과", "031-4321-1111");
            dp2.addDoctor(doctor3);
            dp2.addDoctor(doctor4);

            Department dp3 = new Department("외과", "031-1234-1111");
            dp3.addDoctor(doctor5);

            Address address = new Address("성남", "발이봉남로", "11111");

            Hospital hospital = new Hospital("Hongik Hospital", address, dp1, dp2, dp3);
            hospitalService.join(hospital);
        }
        void userInit() {
            User user1 = new User("환자1", 17, MALE);
            user1.setLogIn("1", "1");
            userService.join(user1);

            User user2 = new User("환자2", 31, MALE);
            user2.setLogIn("2", "2");
            userService.join(user2);

            User user3 = new User("환자3", 64, FEMALE);
            user3.setLogIn("3", "3");
            userService.join(user3);

            User user4 = new User("환자4", 44, FEMALE);
            user4.setLogIn("4", "4");
            userService.join(user4);


        }
        void adminInit() {
            User admin1 = new User("관리자1", 23, MALE);
            admin1.setLogIn("admin", "1111");
            admin1.setAccountType(ADMIN);
            userService.join(admin1);

            User admin2 = new User("관리자2", 36, MALE);
            admin2.setLogIn("god", "kingOfWebSite");
            admin2.setAccountType(ADMIN);
            userService.join(admin2);
        }
    }
}
