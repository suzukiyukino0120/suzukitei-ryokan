package com.example.repository;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.ReservationCalender;
import com.example.domain.Room;

@Mapper
public interface ReservationCalenderMapper {

	public List<ReservationCalender> findEmptyRoom(
			@Param("startDate") LocalDate startDate, 
			@Param("endDate") LocalDate endDate, 
			@Param("roomId") Integer roomId);
	
	public void updateReservationCalender(
			@Param("date") LocalDate date, 
			@Param("stayDays") Integer stayDays, 
			@Param("roomId") Integer roomId);
	
	
	
	
}
