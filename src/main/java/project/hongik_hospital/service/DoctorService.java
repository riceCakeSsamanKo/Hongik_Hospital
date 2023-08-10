package project.hongik_hospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hongik_hospital.domain.Doctor;
import project.hongik_hospital.repository.DoctorRepository;

import java.util.List;

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
        if (doctor.getId() != null) {
            throw new IllegalStateException("이미 존재하는 의사입니다");
        }
    }

    @Transactional(readOnly = true)
    public Doctor findDoctor(Long doctorId) {
        return doctorRepository.findOne(doctorId);
    }

    @Transactional(readOnly = true)
    public List<Doctor> findDoctors() {
        return doctorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Doctor> findDoctors(String name) {
        return doctorRepository.findByName(name);
    }

}
