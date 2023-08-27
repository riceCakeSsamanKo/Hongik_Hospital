package project.hongik_hospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hongik_hospital.domain.*;
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

    public List<Reserve> findReservesByDepartment(Department department) {
        return reserveRepository.findByDepartment(department.getId());
    }

    public void cancel(Long reserveId) {
        Reserve reserve = reserveRepository.findOne(reserveId);
        TreatmentDate treatmentDate = reserve.getTreatmentDate();
        Doctor doctor = reserve.getDoctor();

        // doctor와 reserve간 연관관계 해제, 생김새만 똑같은 객체 new로 넣어줌(표시 용도)
        reserve.setDoctor(new Doctor(doctor.getName(), doctor.getCareer()));
        // doctor와 treatment간 연관관계 해제
        treatmentDate.setDoctor(null);

        // doctor의 treatments 컬렉션에서 treatment 제거
        doctor.cancelTreatment(treatmentDate);
        // treatmentDate를 다시 저장함으로써 doctor의 fk 제거를 DB에 반영
        treatmentDateRepository.save(treatmentDate);

        reserve.cancel();
    }

    public void complete(Long reserveId, int fee) {
        Reserve reserve = reserveRepository.findOne(reserveId);
        TreatmentDate treatmentDate = reserve.getTreatmentDate();
        Doctor doctor = reserve.getDoctor();

        // doctor와 reserve간 연관관계 해제, 생김새만 똑같은 객체 new로 넣어줌(표시 용도)
        reserve.setDoctor(new Doctor(doctor.getName(), doctor.getCareer()));
        // doctor와 treatment간 연관관계 해제
        treatmentDate.setDoctor(null);

        // doctor의 treatments 컬렉션에서 treatment 제거
        doctor.cancelTreatment(treatmentDate);
        // treatmentDate를 다시 저장함으로써 doctor의 fk 제거를 DB에 반영
        treatmentDateRepository.save(treatmentDate);

        reserve.complete(fee);
    }

    /** 업데이트 로직 **/
    // 더티 체킹으로 업데이트 로직 구현
    public void updateDepartment(Long reserveId, Department department) {
        Reserve reserve = reserveRepository.findOne(reserveId);
        reserve.setDepartment(department);
    }
}
