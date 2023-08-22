package project.hongik_hospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hongik_hospital.domain.Doctor;
import project.hongik_hospital.domain.Reserve;
import project.hongik_hospital.domain.ReserveStatus;
import project.hongik_hospital.domain.TreatmentDate;
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
    public List<Reserve> findReserves(String userName) {
        return reserveRepository.findByUserName(userName);
    }
    @Transactional(readOnly = true)
    public List<Reserve> findReserves(ReserveStatus reserveStatus) {
        return reserveRepository.findByStatus(reserveStatus);
    }

    public void cancel(Long reserveId) {
        Reserve reserve = reserveRepository.findOne(reserveId);
        TreatmentDate treatmentDate = reserve.getTreatmentDate();
        Doctor doctor = reserve.getDoctor();

        // doctor의 treatments 컬렉션에서 treatment 제거
        doctor.cancelTreatment(treatmentDate);
        // doctor와 treatment간 연관관계 해제
        treatmentDate.setDoctor(null);
        // treatmentDate를 다시 저장함으로써 doctor의 fk 제거를 DB에 반영
        treatmentDateRepository.save(treatmentDate);

        reserve.cancel();
    }

    public void complete(Long reserveId,int fee) {
        reserveRepository.findOne(reserveId).complete(fee);
    }
}
