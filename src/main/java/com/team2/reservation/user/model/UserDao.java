package com.team2.reservation.user.model;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserDao {
	
	@Select(value = "select * from users where userEmail = #{userEmail}")
	UserVo chklogin(String userEmail);
	
	@Update(value = "insert into users (userName,userEmail,userPw,userPhone) values (#{userName},#{userEmail},#{userPw},#{userPhone})")
	int addInfo(UserVo bean);
}
