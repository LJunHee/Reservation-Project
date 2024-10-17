package com.team2.reservation.user.model;

import org.apache.ibatis.annotations.Update;

public interface UserDao {

	@Update(value = "insert into users (userName,userEmail,userPw,userPhone) values (#{userName},#{userEmail},#{userPw},#{userPhone})")
	int addInfo(UserVo bean);
}
