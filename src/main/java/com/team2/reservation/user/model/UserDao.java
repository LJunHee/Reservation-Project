package com.team2.reservation.user.model;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;

@Mapper
public interface UserDao {
    @Select(value = "SELECT * FROM users WHERE userEmail = #{userEmail}")
    UserVo findByEmail(String userEmail);
    
    @Insert(value = "INSERT INTO users (userName, userEmail, userPw, userPhone) VALUES (#{userName}, #{userEmail}, #{userPw}, #{userPhone})")
    int addUser(UserVo user);
}