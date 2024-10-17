package com.team2.reservation.user.model;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserDao {

	@Update(value = "INSERT INTO users (userName, userEmail, userPw, userPhone) VALUES (#{userName}, #{userEmail}, #{userPw}, #{userPhone})")
	int addInfo(UserVo bean);
}
