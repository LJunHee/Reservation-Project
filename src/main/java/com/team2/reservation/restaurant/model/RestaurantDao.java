package com.team2.reservation.restaurant.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper 
public interface RestaurantDao {
    
	// 페이지와 개수에 따라 레스토랑 목록 가져오기
    @Select("SELECT * FROM restaurant ORDER BY restNo LIMIT #{limit} OFFSET #{offset}")
    List<RestaurantVo> pullList(@Param("offset") int offset, @Param("limit") int limit);
    
    @Select("SELECT * FROM restaurant WHERE restNo = #{restNo}")
    RestaurantVo getList(int restNo);
    
    // 전체 레스토랑 개수 가져오기
    @Select("SELECT COUNT(*) FROM restaurant")
    int getTotalCount();
}