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
    // ë¡œê·¸ì¸ í™•ì¸ (ë¹„ë°€ë²ˆí˜¸ëŠ” ì¡°íšŒí•˜ì§€ ì•ŠìŒ)
    @Select("SELECT userNo, userName, userEmail, userPhone FROM users WHERE userEmail = #{userEmail}")
    UserVo findByEmail(@Param("userEmail") String userEmail);
    
    // ë¹„ë°€ë²ˆí˜¸ í™•ì¸ì„ ìœ„í•œ ë³„ë„ ë©”ì†Œë“œ
    @Select("SELECT userPw FROM users WHERE userEmail = #{userEmail}")
    String getPasswordByEmail(@Param("userEmail") String userEmail);
    
    // ì¤‘ë³µ ì´ë©”ì¼ í™•ì¸
    @Select("SELECT COUNT(*) FROM users WHERE userEmail = #{userEmail}")
    int countByEmail(@Param("userEmail") String userEmail);
    
    // íšŒì›ê°€ìž…
    @Insert("INSERT INTO users (userName, userEmail, userPw, userPhone) VALUES (#{userName}, #{userEmail}, #{userPw}, #{userPhone})")
=======
    //·Î±×ÀÎ È®ÀÎ
    @Select(value = "select * from users where userEmail = #{userEmail}")
    UserVo chklogin(String userEmail);
    
    //Áßº¹ ÀÌ¸ÞÀÏ È®ÀÎ
    @Select(value = "select count(*) from users where userEmail = #{userEmail}")
    int countByEmail(String userEmail);
    
    //È¸¿ø°¡ÀÔ (ºñ¹Ð¹øÈ£´Â ÀÌ¹Ì ¾ÏÈ£È­µÇ¾î Àü´ÞµÊ)
    @Insert(value = "insert into users (userName,userEmail,userPw,userPhone) values (#{userName},#{userEmail},#{userPw},#{userPhone})")
>>>>>>> Stashed changes
    int addInfo(UserVo bean);
}