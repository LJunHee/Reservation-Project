package com.team2.reservation.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team2.reservation.user.model.UserDao;
import com.team2.reservation.user.model.UserVo;

@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
    
    public UserVo login(String userEmail, String userPw) {
        UserVo user = userDao.chklogin(userEmail);
        if (user != null && user.getUserPw().equals(userPw)) {
            return user; // 로그인 성공 시 사용자 정보를 반환
        }
        return null; // 로그인 실패 시 null 반환
    }

    
    public void add(UserVo bean) {
        System.out.println(userDao.addInfo(bean));
    }

}