package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Reservation;
import com.example.domain.Plan;
import com.example.domain.Room;
import com.example.repository.ReservationMapper;
import com.example.repository.PlanMapper;

@Service
@Transactional
public class ReservationService {
	
	@Autowired
	private ReservationMapper reservationMapper;

	@Autowired
	private PlanMapper planMapper;
	
	public void insert(Reservation booking) {
		reservationMapper.insert(booking);
	}
	
	public Plan checkGuestCapaAndCharge(Integer planId) {
		return planMapper.findGuestCapaAndCharge(planId);
		}
	
	
}
