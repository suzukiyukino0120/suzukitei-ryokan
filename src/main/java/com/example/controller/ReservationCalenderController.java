package com.example.controller;


import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.ReservationCalender;
import com.example.domain.Plan;
import com.example.form.SearchEmptyRoomForm;
import com.example.service.ReservationCalenderService;

@Controller
@RequestMapping("/emptyRoom")
public class ReservationCalenderController {
	
	@ModelAttribute
	public SearchEmptyRoomForm setUpSearchRoomForm() {
		return new SearchEmptyRoomForm();
	}
	
	@Autowired 
	private HttpSession session;
	
	@Autowired
	private ReservationCalenderService reservationCalenderService;
	
	@RequestMapping("/toSearchEmptyRoom")
	public String toSearchRoom(Integer planListNum) {
		
		session.removeAttribute("reservationCalender");
		
		List<Plan> planList = (List<Plan>) session.getAttribute("planList");
		
		session.setAttribute("plan", planList.get(planListNum));
		
		return "search_empty_room";
	}
	
	@RequestMapping("/searchEmptyRoom")
	public String searchEmptyRoom(@Validated SearchEmptyRoomForm form, BindingResult result,  Model model){
		
		LocalDate today = LocalDate.now();
		
		if(result.hasErrors()) {
			if(form.getStartDate() != null && form.getEndDate() != null) {
				if((today.compareTo(form.getStartDate()) >= 0) || (today.compareTo(form.getEndDate()) >= 0)) {
					model.addAttribute("dateError", "ñæì˙à»ç~ÇÃì˙ïtÇì¸óÕÇµÇƒÇ≠ÇæÇ≥Ç¢");
				}
			}
			
		return "search_empty_room";
		}
		if((today.compareTo(form.getStartDate()) >= 0) || (today.compareTo(form.getEndDate()) >= 0)) {
			model.addAttribute("dateError", "ñæì˙à»ç~ÇÃì˙ïtÇì¸óÕÇµÇƒÇ≠ÇæÇ≥Ç¢");
			return "search_empty_room";
		}
		
	
		Plan plan = (Plan) session.getAttribute("plan");
		
		List<ReservationCalender> reservationInfo 
			= reservationCalenderService.searchReservedRoom(form.getStartDate(), form.getEndDate(), plan.getRoomId());
	
		
		for(ReservationCalender day: reservationInfo) {
			if(day.getReservedRoom() >= day.getReservableRoom()) {
				day.setEmpty("Å~");
			}else {
				day.setEmpty("ÅZ");
			}
		}
		
		session.setAttribute("reservationCalender", reservationInfo); 
		
		return "search_empty_room";
		
	}

}
