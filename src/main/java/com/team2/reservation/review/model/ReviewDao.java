package com.team2.reservation.review.model;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

@Mapper
public interface ReviewDao {
    // 리뷰 추가
    @Insert("INSERT INTO review (userNo, restNo, reviewContent, reviewScore, createDate) "
            + "VALUES (#{userNo}, #{restNo}, #{reviewContent}, #{reviewScore}, NOW())")
    int addReview(ReviewVo bean);
    
    // 특정 식당의 모든 리뷰 조회
    @Select("SELECT * FROM review WHERE restNo = #{restNo} ORDER BY createDate DESC")
    List<ReviewVo> getReviewsByRestaurant(int restNo);
    
    // 특정 사용자의 모든 리뷰 조회
    @Select("SELECT * FROM review WHERE userNo = #{userNo} ORDER BY createDate DESC")
    List<ReviewVo> getReviewsByUser(int userNo);
    
    // 특정 리뷰 조회
    @Select("SELECT * FROM review WHERE reviewNo = #{reviewNo}")
    ReviewVo getReview(int reviewNo);
    
    // 리뷰 수정
    @Update("UPDATE review SET reviewContent = #{reviewContent}, reviewScore = #{reviewScore} "
            + "WHERE reviewNo = #{reviewNo}")
    int updateReview(ReviewVo bean);
    
    // 리뷰 삭제
    @Delete("DELETE FROM review WHERE reviewNo = #{reviewNo}")
    int deleteReview(int reviewNo);
}