package project.hongik_hospital;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.hongik_hospital.domain.GenderType;
import project.hongik_hospital.domain.Patient;
import project.hongik_hospital.service.DoctorService;
import project.hongik_hospital.service.PatientService;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct  // 빈으로 등록될 시 자동으로 실행됨
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final PatientService patientService;
        private final DoctorService doctorService;
        // 필요시 더 추가할 것

        void dbInit1() {
            Patient patient1 = new Patient("환자1", 110, GenderType.MALE);
            patient1.setLogIn("1", "1");
            patientService.join(patient1);
        }

        void dbInit2() {
            Patient patient2 = new Patient("환자2", 40, GenderType.FEMALE);
            patient2.setLogIn("2", "2");
            patientService.join(patient2);
        }
    }
}
