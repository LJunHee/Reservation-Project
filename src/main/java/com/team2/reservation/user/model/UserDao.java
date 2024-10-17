package com.team2.reservation.user.model;

import org.apache.ibatis.annotations.Update;

public interface UserDao {

	@Update(value = "insert into users (userEmail,userPw,userPhone) values (#{userEmail},#{userPw},#{userPhone})")
	int addInfo(UserVo bean);
}
