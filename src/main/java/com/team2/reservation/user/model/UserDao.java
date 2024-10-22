package com.team2.reservation.user.model;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
<<<<<<< Updated upstream
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
=======
>>>>>>> Stashed changes

@Mapper
public interface UserDao {
    
<<<<<<< Updated upstream
    // 로그인 확인 (비밀번호는 조회하지 않음)
    @Select("SELECT userNo, userName, userEmail, userPhone FROM users WHERE userEmail = #{userEmail}")
    UserVo findByEmail(@Param("userEmail") String userEmail);
    
    // 비밀번호 확인을 위한 별도 메소드
    @Select("SELECT userPw FROM users WHERE userEmail = #{userEmail}")
    String getPasswordByEmail(@Param("userEmail") String userEmail);
    
    // 중복 이메일 확인
    @Select("SELECT COUNT(*) FROM users WHERE userEmail = #{userEmail}")
    int countByEmail(@Param("userEmail") String userEmail);
    
    // 회원가입
    @Insert("INSERT INTO users (userName, userEmail, userPw, userPhone) VALUES (#{userName}, #{userEmail}, #{userPw}, #{userPhone})")
=======
    //�α��� Ȯ��
    @Select(value = "select * from users where userEmail = #{userEmail}")
    UserVo chklogin(String userEmail);
    
    //�ߺ� �̸��� Ȯ��
    @Select(value = "select count(*) from users where userEmail = #{userEmail}")
    int countByEmail(String userEmail);
    
    //ȸ������ (��й�ȣ�� �̹� ��ȣȭ�Ǿ� ���޵�)
    @Insert(value = "insert into users (userName,userEmail,userPw,userPhone) values (#{userName},#{userEmail},#{userPw},#{userPhone})")
>>>>>>> Stashed changes
    int addInfo(UserVo bean);
}