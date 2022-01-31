package com.example.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.ReservationCalender;
import com.example.domain.Room;
import com.example.repository.ReservationCalenderMapper;

@Service
@Transactional
public class ReservationCalenderService {

	@Autowired
	private ReservationCalenderMapper reservationCalenderMapper;
	
	public List<ReservationCalender> searchReservedRoom(LocalDate startDate, LocalDate endDate, Integer roomId){
		return reservationCalenderMapper.findEmptyRoom(startDate, endDate, roomId);
	}
	
	public void updateReservationCalender(LocalDate date, Integer stayDays, Integer roomId) {
		reservationCalenderMapper.updateReservationCalender(date, stayDays, roomId);
	}
}
