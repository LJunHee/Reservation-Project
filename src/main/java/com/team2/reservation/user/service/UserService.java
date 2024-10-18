package com.team2.reservation.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team2.reservation.user.model.UserDao;
import com.team2.reservation.user.model.UserVo;

@Service
public class UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }
    
    public UserVo login(String userEmail, String userPw) {
        UserVo user = userDao.findByEmail(userEmail);
        if (user != null && passwordEncoder.matches(userPw, user.getUserPw())) {
            return user; // 로그인 성공 시 사용자 정보를 반환
        }
        return null; // 로그인 실패 시 null 반환
    }
    
    public boolean isEmailAlreadyRegistered(String email) {
        return userDao.findByEmail(email) != null;
    }
    
    @Transactional
    public void register(UserVo user) {
        if (user.getUserName() == null || user.getUserName().isEmpty()) {
            throw new IllegalArgumentException("사용자 이름은 필수입니다.");
        }
        if (user.getUserEmail() == null || user.getUserEmail().isEmpty()) {
            throw new IllegalArgumentException("이메일은 필수입니다.");
        }
        if (user.getUserPw() == null || user.getUserPw().isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 필수입니다.");
        }

//        // 이메일 중복 체크
//        if (userDao.findByEmail(user.getUserEmail()) != null) {
//            throw new IllegalStateException("이미 등록된 이메일입니다.");
//        }

        // 비밀번호 암호화
        user.setUserPw(passwordEncoder.encode(user.getUserPw()));

        int result = userDao.addUser(user);
        if (result != 1) {
            throw new RuntimeException("사용자 등록에 실패했습니다.");
        }
    }
}