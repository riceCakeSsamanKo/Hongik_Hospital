package project.hongik_hospital.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.hongik_hospital.domain.Department;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class DepartmentRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    DepartmentRepository departmentRepository;

    @Test
    public void remove() throws Exception{
        //given
        Department department = new Department("temp","1234");
        departmentRepository.save(department);

        //when
        departmentRepository.remove(department);

        //then
        Assertions.assertThat(departmentRepository.findByName("temp")).isEqualTo(Optional.empty()) ;
    }

    @Test
    public void removeError() throws Exception{
        //given
        Department department = new Department("temp","1234");
        //when
        departmentRepository.remove(department);

        //then
        //오 delete문은 삭제할 데이터가 없더라도 에러발생 안함!!
        departmentRepository.remove(department);
    }
}