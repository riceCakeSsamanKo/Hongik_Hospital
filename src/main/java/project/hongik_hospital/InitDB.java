package project.hongik_hospital;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.hongik_hospital.domain.*;
import project.hongik_hospital.repository.HospitalRepository;
import project.hongik_hospital.service.DoctorService;
import project.hongik_hospital.service.HospitalService;
import project.hongik_hospital.service.PatientService;

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
        initService.patientInit();
        initService.adminInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final PatientService patientService;
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
        void patientInit() {
            Patient patient1 = new Patient("환자1", 17, MALE);
            patient1.setLogIn("1", "1");
            patientService.join(patient1);

            Patient patient2 = new Patient("환자2", 31, MALE);
            patient2.setLogIn("2", "2");
            patientService.join(patient2);

            Patient patient3 = new Patient("환자3", 64, FEMALE);
            patient3.setLogIn("3", "3");
            patientService.join(patient3);

            Patient patient4 = new Patient("환자4", 44, FEMALE);
            patient4.setLogIn("4", "4");
            patientService.join(patient4);


        }
        void adminInit() {
            Patient admin1 = new Patient("관리자1", 23, GenderType.MALE);
            admin1.setLogIn("admin", "1111");
            admin1.setAccountType(ADMIN);
            patientService.join(admin1);

            Patient admin2 = new Patient("관리자2", 36, GenderType.MALE);
            admin2.setLogIn("god", "kingOfWebSite");
            admin2.setAccountType(ADMIN);
            patientService.join(admin2);
        }
    }
}
