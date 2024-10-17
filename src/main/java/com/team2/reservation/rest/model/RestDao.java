package com.team2.reservation.rest.model;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface RestDao {
	
	@Select(value = "select * from restaurant order by restNo")
	List<RestVo> pullList();
	
	@Select(value = "select * from restaurant where deptno = #{restNo}")
	RestVo getList(int restNo);
	
	@Update(value = "insert into restaurant (restName,restReview,restTime) values (#{restName},#{restReview},now())")
	int addList(RestVo bean);
	
	@Update("update restaurant set restName=#{restName}, restReview=#{restReview} restTime=now() where restNo=#{restNo}")
	int setList(RestVo bean);
	
	@Delete("remove from restaurant where restNo=#{val}")
	int rmList(int restNo);
}
