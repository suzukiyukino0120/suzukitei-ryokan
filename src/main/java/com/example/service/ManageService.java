package com.example.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Plan;
import com.example.domain.Reservation;
import com.example.domain.ReservationCalender;
import com.example.repository.PlanMapper;
import com.example.repository.ReservationCalenderMapper;
import com.example.repository.ReservationMapper;

@Service
@Transactional
public class ManageService {
	@Autowired
	private PlanMapper planMapper;
	
	@Autowired
	private ReservationMapper reservationMapper;
	
	@Autowired
	private ReservationCalenderMapper reservationCalenderMapper;
	
	
	public List<Plan>findAllPlan(){
		return planMapper.findAll();
	}
	
	public Plan findPlanById(Integer id) {
		return planMapper.findById(id);
	}
	
	public void insertPlan(Plan plan) {
		planMapper.insert(plan);
	}
	
	public void updatePlan(Plan plan) {
		planMapper.update(plan);
	}
	
	public void deletePlan(Integer planId) {
		planMapper.delete(planId);
	}
	
	public List<Reservation> find(Reservation reservation){
		return reservationMapper.find(reservation);
		
	}
	
	public Reservation findReservationById(Integer id) {
		return reservationMapper.findById(id);
	}
	
	public void cancelReservation(Integer id) {
		reservationMapper.delete(id);
	}
	
	public List<ReservationCalender> findAllReservationLimit(LocalDate startDate, LocalDate endDate){
		return reservationCalenderMapper.findAllReservationLimit(startDate, endDate);
	}
	
	public void updateReservationLimit(List<ReservationCalender> reservationCalender) {
		reservationCalenderMapper.updateReservationLimit(reservationCalender);
	}
	
	
}
