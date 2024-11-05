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
    
    // 최근 등록된 8개의 레스토랑 가져오기
    @Select("SELECT * FROM restaurant ORDER BY restNo DESC LIMIT 8")
    List<RestaurantVo> getRecentRestaurants();
    
    // 검색
    @Select("SELECT * FROM restaurant WHERE restName LIKE CONCAT('%', #{restName}, '%')")
    List<RestaurantVo> search(String restName);
    
    // 전체 레스토랑 개수 가져오기
    @Select("SELECT COUNT(*) FROM restaurant")
    int getTotalCount();
    
    // 인기 레스토랑
    @Select("SELECT r.*, COALESCE(AVG(rv.reviewScore), 0) as avgScore " +
            "FROM restaurant r " +
            "LEFT JOIN review rv ON r.restNo = rv.restNo " +
            "GROUP BY r.restNo " +
            "ORDER BY avgScore DESC " +
            "LIMIT 8")
    List<RestaurantVo> getPopularRestaurants();
}