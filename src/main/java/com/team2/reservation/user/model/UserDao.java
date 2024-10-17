package com.team2.reservation.user.model;

import org.apache.ibatis.annotations.Insert;

public interface UserDao {

	@Insert("INSERT INTO users (userName, userEmail, userPw, userPhone) VALUES (#{userName}, #{userEmail}, #{userPw}, #{userPhone})")
	int addInfo(UserVo bean);
}
