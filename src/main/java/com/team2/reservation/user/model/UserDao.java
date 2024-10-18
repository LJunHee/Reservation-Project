package com.team2.reservation.user.model;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserDao {
	
	//로그인 확인
	@Select(value = "select * from users where userEmail = #{userEmail}")
	UserVo chklogin(String userEmail);
	
	//중복 이메일 확인
	@Select(value = "select count(*) from users where userEmail = #{userEmail}")
	int countByEmail(String userEmail);
	
	//회원가입
	@Insert(value = "insert into users (userName,userEmail,userPw,userPhone) values (#{userName},#{userEmail},#{userPw},#{userPhone})")
	int addInfo(UserVo bean);
}