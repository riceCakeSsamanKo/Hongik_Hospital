package project.hongik_hospital.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;
import project.hongik_hospital.domain.GenderType;
import project.hongik_hospital.domain.User;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    UserRepository userRepository;

    @BeforeEach //각 테스트마다 데이터 주입
    public void each() {
        User user = new User("환자", 30, GenderType.MALE);
        user.setLogIn("id", "pw");
        userRepository.save(user);
    }

    @Test
    void find() {
        List<User> user = userRepository.findByName("환자1");
    }

    @Test
    void findByLogInfo() {
        Optional<User> byLogInfoOptional = userRepository.findByLogInfo("id", "pw");
        User byLogInfo = byLogInfoOptional.get();
        System.out.println("byLogInfo = " + byLogInfo.getName());

        assertThat(byLogInfo.getName()).isEqualTo("환자");
    }

    @Test
    public void remove() throws Exception {
        //given
        List<User> findUser = userRepository.findByName("환자");
        User user = findUser.get(0);

        //when
        userRepository.remove(user);

        //then
        assertThat(userRepository.findByName("환자").size()).isEqualTo(0);
        assertThrows(InvalidDataAccessApiUsageException.class, () -> userRepository.remove(user));
    }
}