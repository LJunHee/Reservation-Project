package com.team2.reservation.user.service;

import org.springframework.stereotype.Service;

import com.team2.reservation.user.model.UserDao;
import com.team2.reservation.user.model.UserVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	final UserDao userDao;
	
	public void add(UserVo bean) {
		System.out.println(userDao.addInfo(bean));
	}
}
