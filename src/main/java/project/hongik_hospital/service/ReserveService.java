package project.hongik_hospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hongik_hospital.domain.Doctor;
import project.hongik_hospital.domain.Reserve;
import project.hongik_hospital.domain.ReserveStatus;
import project.hongik_hospital.repository.DoctorRepository;
import project.hongik_hospital.repository.ReserveRepository;
import project.hongik_hospital.repository.TreatmentDateRepository;


import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReserveService {

    private final ReserveRepository reserveRepository;
    private final TreatmentDateRepository treatmentDateRepository;

    public void saveReserve(Reserve reserve) {
        reserveRepository.save(reserve);
    }

    @Transactional(readOnly = true)
    public Reserve findReserve(Long reserveId) {
        return reserveRepository.findOne(reserveId);
    }
    @Transactional(readOnly = true)
    public List<Reserve> findReserves() {
        return reserveRepository.findAll();
    }
    @Transactional(readOnly = true)
    public List<Reserve> findReserves(String patientName) {
        return reserveRepository.findByPatientName(patientName);
    }
    @Transactional(readOnly = true)
    public List<Reserve> findReserves(ReserveStatus reserveStatus) {
        return reserveRepository.findByStatus(reserveStatus);
    }

    public void cancel(Long reserveId) {
        Reserve reserve = reserveRepository.findOne(reserveId);
        reserve.cancel();
        treatmentDateRepository.remove(reserve.getTreatmentDate().getId());
    }

    public void complete(Long reserveId,int fee) {
        reserveRepository.findOne(reserveId).complete(fee);
    }
}
