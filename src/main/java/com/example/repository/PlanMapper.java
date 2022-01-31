package com.example.repository;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.Plan;
import com.example.domain.Room;

@Mapper
public interface PlanMapper {
	public List<Plan> findPlanByDate(
			@Param("smoking") Integer smoking, 
			@Param("bathroom") Integer bathroom, 
			@Param("breakfast") Integer breakfast, 
			@Param("dinner") Integer dinner,
			@Param("date") LocalDate date, 
			@Param("stayDays") Integer stayDays, 
			@Param("numOfGuest") Integer numOfGuest
			);
	
	public List<Plan> findPlan(
			@Param("smoking") Integer smoking, 
			@Param("bathroom") Integer bathroom, 
			@Param("breakfast") Integer breakfast, 
			@Param("dinner") Integer dinner, 
			@Param("planId") Integer planId, 
			@Param("numOfGuest") Integer numOfGuest);
	
	public Plan findGuestCapaAndCharge(Integer planId);
	
	
	
	
	/*
	public List<Room> findPlanByDate(
			@Param("smoking") Integer smoking, 
			@Param("bathroom") Integer bathroom, 
			@Param("breakfast") Integer breakfast, 
			@Param("dinner") Integer dinner, 
			@Param("date") LocalDate date,
			@Param("stayDays") Integer stayDays);
	
	public List<Room> findRoom();
	
	*/

}
