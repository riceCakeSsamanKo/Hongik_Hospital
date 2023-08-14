package project.hongik_hospital.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hongik_hospital.domain.GenderType;
import project.hongik_hospital.domain.Patient;
import project.hongik_hospital.repository.PatientRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PatientService {

    private final PatientRepository patientRepository;

    public Long join(Patient patient) {
        patientRepository.save(patient);
        return patient.getId();
    }

    @Transactional(readOnly = true)
    public Patient findPatient(Long patientId) {
        return patientRepository.findOne(patientId);
    }

    @Transactional(readOnly = true)
    public Optional<Patient> findPatient(String id, String pw) {
        return patientRepository.findByLogInfo(id, pw);
    }

    @Transactional(readOnly = true)
    public List<Patient> findPatients() {
        return patientRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Patient> findPatients(String name) {
        return patientRepository.findByName(name);
    }

    public void update(Long id, String loginId, String loginPw, String name, int age, GenderType gender) {
        Patient patient = patientRepository.findOne(id);
        patient.update(loginId, loginPw, name, age, gender);
    }

    public boolean removePatient(Long patientId) {
        try {
            Patient patient = patientRepository.findOne(patientId);
            patientRepository.remove(patient);
        } catch (Exception e) {
            log.info("e = " + e);
            return false;
        }
        return true;
    }

    public boolean removePatient(Patient patient) {
        try {
            patientRepository.remove(patient);
        } catch (Exception e) {
            log.info("e = " + e);
            return false;
        }
        return true;
    }

    public boolean removePatient(String id, String pw) {
        Optional<Patient> findPatient = patientRepository.findByLogInfo(id, pw);
        Patient patient = findPatient.get();
        try {
            patientRepository.remove(patient);
        } catch (Exception e) {
            log.info("e = " + e);
            return false;
        }
        return true;
    }

}
