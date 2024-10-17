package com.team2.reservation.restaurant.model;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper 
public interface RestaurantDao {
    
    @Select("SELECT * FROM restaurant ORDER BY restNo")
    List<RestaurantVo> pullList();
    
    @Select("SELECT * FROM restaurant WHERE restNo = #{restNo}")
    RestaurantVo getList(int restNo);
}
