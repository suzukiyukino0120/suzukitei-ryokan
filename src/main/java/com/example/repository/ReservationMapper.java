package com.example.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.Reservation;

@Mapper
public interface ReservationMapper {
	public void insert(Reservation booking);
	
}
