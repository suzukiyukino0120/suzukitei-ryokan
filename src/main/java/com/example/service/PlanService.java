package com.example.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Plan;
import com.example.domain.Room;
import com.example.repository.PlanMapper;

@Service
@Transactional
public class PlanService {

	@Autowired
	private PlanMapper planMapper;
	
	public List<Plan> searchPlanByDate(Integer smoking, Integer bathroom, Integer breakfast, Integer dinner, LocalDate date, Integer stayDays, Integer numOfGuest){
		return planMapper.findPlanByDate(smoking, bathroom, breakfast, dinner, date, stayDays, numOfGuest);
	}
	
	public List<Plan> searchPlan(Integer smoking, Integer bathroom, Integer breakfast, Integer dinner, Integer planId, Integer numOfGuest){
		return planMapper.findPlan(smoking, bathroom, breakfast, dinner, planId, numOfGuest);
	}
	
	/*
	public List<Room> searchPlanByDate(Integer smoking, Integer bathroom, Integer breakfast, Integer dinner, LocalDate date, Integer stayDays){
		return planMapper.findPlanByDate(smoking, bathroom, breakfast, dinner, date, stayDays);
	}
	*/
}
