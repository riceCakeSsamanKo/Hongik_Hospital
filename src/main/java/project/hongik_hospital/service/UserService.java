package project.hongik_hospital.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hongik_hospital.domain.GenderType;
import project.hongik_hospital.domain.User;
import project.hongik_hospital.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public Long join(User user) {
        userRepository.save(user);
        return user.getId();
    }

    @Transactional(readOnly = true)
    public User findUser(Long userId) {
        return userRepository.findOne(userId);
    }

    @Transactional(readOnly = true)
    public Optional<User> findUser(String id, String pw) {
        return userRepository.findByLogInfo(id, pw);
    }

    @Transactional(readOnly = true)
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<User> findUsers(String name) {
        return userRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserByLoginId(String login_id) {
        return userRepository.findByLogInfo(login_id);
    }

    /** 업데이트 로직 **/
    // 더티 체킹으로 업데이트 로직 구현
    public void update(Long id, String loginId, String loginPw, String name, int age, GenderType gender) {
        User user = userRepository.findOne(id);

        user.setName(name);
        user.setLogIn(loginId, loginPw);
        user.setAge(age);
        user.setSex(gender);
    }

    public boolean removeUser(Long userId) {
        try {
            User user = userRepository.findOne(userId);
            userRepository.remove(user);
        } catch (Exception e) {
            log.info("e = " + e);
            return false;
        }
        return true;
    }

    public boolean removeUser(User user) {
        try {
            userRepository.remove(user);
        } catch (Exception e) {
            log.info("e = " + e);
            return false;
        }
        return true;
    }

    public boolean removeUser(String id, String pw) {
        Optional<User> findUser = userRepository.findByLogInfo(id, pw);
        User user = findUser.get();
        try {
            userRepository.remove(user);
        } catch (Exception e) {
            log.info("e = " + e);
            return false;
        }
        return true;
    }

}
