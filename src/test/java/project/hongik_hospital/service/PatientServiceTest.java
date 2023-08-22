package project.hongik_hospital.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import project.hongik_hospital.domain.GenderType;
import project.hongik_hospital.domain.User;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    EntityManager em;

    @BeforeEach
    public void each() {
        User user1 = new User("user", 10, GenderType.MALE);
        User user2 = new User("user", 20, GenderType.FEMALE);
        user2.setLogIn("id","pw");

        userService.join(user1);
        userService.join(user2);
    }

    @Test
    public void remove() throws Exception{
        //given
        List<User> findUsers = userService.findUsers("user");
        User user1 = findUsers.get(0);
        User user2 = findUsers.get(1);

        //when
        boolean bool1 = userService.removeUser(user1);
        boolean bool2 = userService.removeUser("id", "pw");
        boolean bool3 = userService.removeUser(user1);  // 또 제거한 경우 false

        //then
        assertEquals(bool1, true);
        assertEquals(bool2, true);
        assertEquals(bool3, false);
        assertEquals(userService.findUsers("user").size(),0);
    }
    @Test
    public void dirtyCheck() throws Exception{
        //given
        User user = userService.findUser(1L);

        //when
        user.setAge(12314124);
        //then

    }
}