package project.hongik_hospital.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import project.hongik_hospital.domain.Department;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class DepartmentServiceTest {
    @Autowired
    DepartmentService departmentService;

    @Autowired
    EntityManager em;
    @BeforeEach
    public void each() {
        Department department1 = new Department("dp1","1");
        Department department2 = new Department("dp2","2");
        Department department3 = new Department("dp3","3");
        Department department4 = new Department("dp4","4");

        departmentService.join(department1);
        departmentService.join(department2);
        departmentService.join(department3);
        departmentService.join(department4);
    }
    @Test
    public void findByPhoneNumber() throws Exception {
        if(departmentService.findDepartmentByPhoneNumber("031-1111-1111")

    }

    @Test
    public void findByName() throws Exception {
        //when
        Optional<Department> findDp1 = departmentService.findDepartmentByName("dp1");

        if (findDp1.isEmpty()) {
            System.out.println("없음");
        }

        Department dp1 = findDp1.get();
        System.out.println("dp1.getName() = " + dp1.getId());
        System.out.println("dp1.getName() = " + dp1.getName());
        System.out.println("dp1.getName() = " + dp1.getPhoneNumber());

        //then
        assertThat(dp1.getName()).isEqualTo("dp1");
        assertThat(dp1.getPhoneNumber()).isEqualTo("1");

    }
}