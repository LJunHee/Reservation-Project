package com.team2.reservation.restaurant.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper 
public interface RestaurantDao {
    
    // �������� ������ ���� ������� ��� ��������
    @Select("SELECT * FROM restaurant ORDER BY restNo LIMIT #{limit} OFFSET #{offset}")
    List<RestaurantVo> pullList(@Param("offset") int offset, @Param("limit") int limit);
    
    // Ư�� ������� ���� ���� ��������
    @Select("SELECT * FROM restaurant WHERE restNo = #{restNo}")
    RestaurantVo getList(int restNo);
    
    // ��ü ������� ���� ��������
    @Select("SELECT COUNT(*) FROM restaurant")
    int getTotalCount();
}