		package com.team2.reservation.resrve.model;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ReserveDao {
	
	@Select(value = "select * from restaurant order by restNo")
	List<ReserveVo> pullList();
	
	@Select(value = "select * from restaurant where deptno = #{restNo}")
	ReserveVo getList(int restNo);
	
	@Update(value = "insert into restaurant (restName,restReview,restTime) values (#{restName},#{restReview},now())")
	int addList(ReserveVo bean);
	
	@Update("update restaurant set restName=#{restName}, restReview=#{restReview} restTime=now() where restNo=#{restNo}")
	int setList(ReserveVo bean);
	
	@Delete("remove from restaurant where restNo=#{val}")
	int rmList(int restNo);
}