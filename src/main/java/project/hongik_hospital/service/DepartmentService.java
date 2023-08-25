package project.hongik_hospital.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hongik_hospital.domain.Department;
import project.hongik_hospital.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public void join(Department department) {
        departmentRepository.save(department);
    }

    @Transactional(readOnly = true)
    public Department findDepartment(Long departmentId) {
        return departmentRepository.findOne(departmentId);
    }

    @Transactional(readOnly = true)
    public Optional<Department> findDepartmentByName(String departmentName) {
        return departmentRepository.findByName(departmentName);
    }

    @Transactional(readOnly = true)
    public Optional<Department> findDepartmentByPhoneNumber(String phoneNumber) {
        return departmentRepository.findByPhoneNumber(phoneNumber);
    }

    @Transactional(readOnly = true)
    public List<Department> findDepartments() {
        return departmentRepository.findAll();
    }

    public void removeDepartment(Department department) {
        departmentRepository.remove(department);
    }
}