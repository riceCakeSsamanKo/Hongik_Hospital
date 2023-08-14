package project.hongik_hospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hongik_hospital.domain.Patient;
import project.hongik_hospital.repository.PatientRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
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

    public boolean removePatient(Patient patient) {
        try {
            patientRepository.remove(patient);
        } catch (Exception e) {
            System.out.println("e = " + e);
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
            System.out.println("e = " + e);
            return false;
        }
        return true;
    }

}
