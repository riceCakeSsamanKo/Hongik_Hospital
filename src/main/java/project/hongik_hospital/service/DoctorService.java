package project.hongik_hospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hongik_hospital.domain.Doctor;
import project.hongik_hospital.repository.DoctorRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public Long join(Doctor doctor){
        isDocAlreadyExist(doctor);
        doctorRepository.save(doctor);
        return doctor.getId();
    }

    private void isDocAlreadyExist(Doctor doctor) {
        if (doctorRepository.findOne(doctor.getId()) != null) {
            throw new IllegalStateException("이미 존재하는 의사입니다");
        }
    }
}
