package project.hongik_hospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hongik_hospital.domain.Hospital;
import project.hongik_hospital.repository.HospitalRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HospitalService {
    private final HospitalRepository hospitalRepository;

    public Long join(Hospital hospital){
        isHospitalAlreadyExist(hospital);
        hospitalRepository.save(hospital);
        return hospital.getId();
    }

    private void isHospitalAlreadyExist(Hospital hospital) {
        if (hospital.getId() != null) {
            throw new IllegalStateException("이미 존재하는 병원입니다");
        }
    }

    @Transactional(readOnly = true)
    public Hospital findHospital(Long hospitalId) {
        return hospitalRepository.findOne(hospitalId);
    }

    @Transactional(readOnly = true)
    public List<Hospital> findHospitals() {
        return hospitalRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Hospital> findHospitals(String name) {
        return hospitalRepository.findByName(name);
    }

}
